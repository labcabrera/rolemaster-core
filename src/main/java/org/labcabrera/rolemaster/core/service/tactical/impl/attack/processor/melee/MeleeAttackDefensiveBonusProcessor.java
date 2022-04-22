package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalCharacterItemService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalCharacterStatusService;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class MeleeAttackDefensiveBonusProcessor {

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	@Autowired
	private TacticalCharacterItemService itemService;

	@Autowired
	private TacticalCharacterStatusService statusService;

	public Mono<MeleeAttackContext> apply(MeleeAttackContext context) {
		if (context.getAction().isFlumbe()) {
			return Mono.just(context);
		}
		return Mono.just(context)
			.flatMap(e -> processShieldAndSParryBonus(context, AttackTargetType.MAIN_HAND))
			.flatMap(e -> processShieldAndSParryBonus(context, AttackTargetType.OFF_HAND));
	}

	private Mono<MeleeAttackContext> processShieldAndSParryBonus(MeleeAttackContext context, AttackTargetType type) {
		TacticalCharacter tc = context.getTargets().get(type);
		if (tc == null) {
			return Mono.just(context);
		}
		String roundId = context.getAction().getRoundId();
		String targetId = tc.getId();
		return actionRepository.findByRoundIdAndSource(roundId, targetId)
			.collectList()
			.flatMap(list -> processParry(context, list, type));
	}

	private Mono<MeleeAttackContext> processParry(MeleeAttackContext context, List<TacticalAction> targetActions, AttackTargetType type) {
		TacticalCharacter target = context.getTargets().get(type);
		boolean checkUpdateParry = false;
		TacticalActionMeleeAttack attack = null;

		// NOTE There may be more than one attack in the case of combat with two weapons against the same target.
		// In this case the parry value must be the same for both attacks.
		List<TacticalActionMeleeAttack> attacks = targetActions.stream()
			.filter(TacticalActionMeleeAttack.class::isInstance)
			.map(TacticalActionMeleeAttack.class::cast)
			.filter(e -> e.getTargets().values().contains(context.getSource().getId()))
			.toList();

		// Search for melee attacks that do not have specified targets (press and melee & react and melee).
		if (attacks.isEmpty()) {
			attacks = targetActions.stream()
				.filter(TacticalActionMeleeAttack.class::isInstance)
				.map(TacticalActionMeleeAttack.class::cast)
				.filter(e -> e.getParry() > 0)
				.filter(e -> notRequiredTarget(e))
				.filter(e -> e.getParried() == false)
				.toList();
			checkUpdateParry = !attacks.isEmpty();
		}

		attack = attacks.isEmpty() ? null : attacks.iterator().next();

		int parry = 0;
		int shield = 0;

		if (attack != null) {
			if (statusService.canParry(target)) {
				parry = attack.getParry();
			}
			if (statusService.canBlock()) {
				CharacterItem item = itemResolver.getItem(target, ItemPosition.OFF_HAND);
				shield = itemService.getShieldBonus(item);
			}
		}

		context.getAction().getOffensiveBonusMap().get(type).put(OffensiveBonusModifier.PARRY, parry);
		context.getAction().getOffensiveBonusMap().get(type).put(OffensiveBonusModifier.SHIELD, shield);

		if (checkUpdateParry && attack != null) {
			attack.setParried(true);
			return actionRepository.save(attack).map(e -> context);
		}
		else {
			return Mono.just(context);
		}
	}

	private boolean notRequiredTarget(TacticalActionMeleeAttack attack) {
		return attack.getMeleeAttackType() == MeleeAttackType.PRESS_AND_MELEE
			|| attack.getMeleeAttackType() == MeleeAttackType.REACT_AND_MELEE;
	}

}
