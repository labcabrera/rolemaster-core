package org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor;

import java.util.List;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.services.rmss.tactical.TacticalCharacterItemResolver;
import org.labcabrera.rolemaster.core.services.rmss.tactical.TacticalCharacterItemService;
import org.labcabrera.rolemaster.core.services.rmss.tactical.TacticalCharacterStatusService;
import org.labcabrera.rolemaster.core.services.tactical.attack.processor.AbstractAttackProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class MeleeAttackDefensiveBonusProcessor implements AbstractAttackProcessor {

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	@Autowired
	private TacticalCharacterItemService itemService;

	@Autowired
	private TacticalCharacterStatusService statusService;

	@Override
	public Mono<AttackContext> apply(AttackContext context) {
		if (context.getAction().isFlumbe() || !(context.getAction() instanceof TacticalActionMeleeAttack)) {
			return Mono.just(context);
		}
		return Mono.just(context)
			.flatMap(e -> processShieldAndSParryBonus(context, AttackTargetType.MAIN_HAND))
			.flatMap(e -> processShieldAndSParryBonus(context, AttackTargetType.OFF_HAND));
	}

	private Mono<AttackContext> processShieldAndSParryBonus(AttackContext context, AttackTargetType type) {
		TacticalCharacter tc = context.getTargets().get(type);
		if (tc == null) {
			return Mono.just(context);
		}
		String roundId = context.getAction().getRoundId();
		String targetId = tc.getId();
		return actionRepository.findByRoundIdAndSource(roundId, targetId)
			.collectList()
			.flatMap(list -> processShieldAndSParryBonus(context, list, type));
	}

	private Mono<AttackContext> processShieldAndSParryBonus(AttackContext context, List<TacticalAction> actions,
		AttackTargetType type) {
		return processParry(context, actions, type).then(processShield(context, actions, type));
	}

	private Mono<AttackContext> processParry(AttackContext context, List<TacticalAction> targetActions, AttackTargetType type) {
		TacticalCharacter target = context.getTargets().get(type);
		boolean checkUpdateParry = false;

		// NOTE There may be more than one attack in the case of combat with two weapons against the same target.
		// In this case the parry value must be the same for both attacks.
		List<TacticalActionMeleeAttack> attacks = targetActions.stream()
			.filter(TacticalActionMeleeAttack.class::isInstance)
			.map(TacticalActionMeleeAttack.class::cast)
			.filter(this::notParried)
			.filter(e -> e.getTargets().values().contains(context.getSource().getId()))
			.toList();

		// Search for melee attacks that do not have specified targets (press and melee & react and melee).
		if (attacks.isEmpty()) {
			attacks = targetActions.stream()
				.filter(TacticalActionMeleeAttack.class::isInstance)
				.map(TacticalActionMeleeAttack.class::cast)
				.filter(this::notRequiredTarget)
				.filter(this::notParried)
				.toList();
			checkUpdateParry = !attacks.isEmpty();
		}
		if (attacks.isEmpty()) {
			return Mono.just(context);
		}
		final TacticalActionMeleeAttack attack = attacks.iterator().next();
		final boolean update = checkUpdateParry;
		return Mono.just(context)
			.map(e -> processParry(context, attack, target, type))
			.flatMap(e -> updateParryActionIfRequired(context, attack, update));
	}

	private Mono<AttackContext> processShield(AttackContext context, List<TacticalAction> targetActions,
		AttackTargetType type) {
		TacticalCharacter target = context.getTargets().get(type);
		boolean checkUpdateParry = false;

		// NOTE There may be more than one attack in the case of combat with two weapons against the same target.
		// In this case the parry value must be the same for both attacks.
		List<TacticalActionMeleeAttack> attacks = targetActions.stream()
			.filter(TacticalActionMeleeAttack.class::isInstance)
			.map(TacticalActionMeleeAttack.class::cast)
			.filter(this::notBloked)
			.filter(e -> e.getTargets().values().contains(context.getSource().getId()))
			.toList();

		// Search for melee attacks that do not have specified targets (press and melee & react and melee).
		if (attacks.isEmpty()) {
			attacks = targetActions.stream()
				.filter(TacticalActionMeleeAttack.class::isInstance)
				.map(TacticalActionMeleeAttack.class::cast)
				.filter(this::notRequiredTarget)
				.filter(this::notBloked)
				.toList();
			checkUpdateParry = !attacks.isEmpty();
		}
		if (attacks.isEmpty()) {
			return Mono.just(context);
		}
		final TacticalActionMeleeAttack attack = attacks.iterator().next();
		final boolean update = checkUpdateParry;
		return Mono.just(context)
			.map(e -> processShield(context, target, type))
			.flatMap(e -> updateBlockedActionIfRequired(context, attack, update));
	}

	private AttackContext processParry(AttackContext context, TacticalActionMeleeAttack attack, TacticalCharacter target,
		AttackTargetType type) {
		int parry = 0;
		if (statusService.canParry(target)) {
			parry = Math.abs(attack.getParry());
		}
		context.getAction().getOffensiveBonusMap().get(type).put(OffensiveBonusModifier.PARRY_DEFENSE, -parry);
		return context;
	}

	private Mono<AttackContext> processShield(AttackContext context, TacticalCharacter target, AttackTargetType type) {
		CharacterItem item = itemResolver.getItem(target, ItemPosition.OFF_HAND);
		int bonus = 0;
		if (statusService.canBlock()) {
			bonus = itemService.getShieldBonus(item);
		}
		context.getAction().getOffensiveBonusMap().get(type).put(OffensiveBonusModifier.SHIELD, bonus);
		return Mono.just(context);
	}

	private Mono<AttackContext> updateParryActionIfRequired(AttackContext context, TacticalActionMeleeAttack attack,
		boolean checkUpdateParry) {
		if (checkUpdateParry && attack != null) {
			attack.setParried(true);
			return actionRepository.save(attack).thenReturn(context);
		}
		else {
			return Mono.just(context);
		}
	}

	private Mono<AttackContext> updateBlockedActionIfRequired(AttackContext context, TacticalActionMeleeAttack attack,
		boolean checkUpdateBlock) {
		if (checkUpdateBlock && attack != null) {
			attack.setBlocked(true);
			return actionRepository.save(attack).thenReturn(context);
		}
		else {
			return Mono.just(context);
		}
	}

	private boolean notRequiredTarget(TacticalActionMeleeAttack attack) {
		return attack.getMeleeAttackType() == MeleeAttackType.PRESS_AND_MELEE
			|| attack.getMeleeAttackType() == MeleeAttackType.REACT_AND_MELEE;
	}

	private boolean notParried(TacticalActionMeleeAttack attack) {
		return attack.getParry() > 0 && !attack.isParried();

	}

	private boolean notBloked(TacticalActionMeleeAttack attack) {
		return !attack.isBlocked();
	}
}
