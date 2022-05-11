package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.LinkedHashMap;
import java.util.List;

import org.labcabrera.rolemaster.core.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.character.SpecialAttack;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.service.context.AttackContext;
import org.labcabrera.rolemaster.core.service.tactical.TacticalSkillService;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.ob.OBProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class OffensiveBonusProcessor implements AbstractAttackProcessor {

	@Autowired
	private TacticalSkillService skillService;

	@Autowired
	private TacticalCharacterItemResolver characterItemResolver;

	@Autowired
	private List<OBProcessor> obProcessors;

	public Mono<AttackContext> apply(AttackContext context) {
		if (context.getAction().getState() != TacticalActionState.PENDING) {
			return Mono.just(context);
		}
		log.debug("Processing offensive bonus");
		return Mono.just(context)
			.map(this::initialize)
			.flatMap(this::loadSkillBonus)
			.map(this::loadProcessors)
			.map(this::cleanUp);
	}

	private AttackContext initialize(AttackContext context) {
		context.getAction().getOffensiveBonusMap().put(AttackTargetType.MAIN_HAND, new LinkedHashMap<>());
		context.getAction().getOffensiveBonusMap().put(AttackTargetType.OFF_HAND, new LinkedHashMap<>());
		return context;
	}

	private Mono<AttackContext> loadSkillBonus(AttackContext context) {
		TacticalCharacter source = context.getSource();
		String skillId;
		if (context.getAction().getSpecialAttack() != null) {
			String specialAttackName = context.getAction().getSpecialAttack();
			SpecialAttack specialAttack = context.getSource().getSpecialAttacks().stream()
				.filter(e -> e.getName().equals(specialAttackName))
				.findFirst().orElseThrow(() -> new DataConsistenceException("Missing special attack " + specialAttackName + "."));
			skillId = specialAttack.getSkillId();
		}
		else {
			CharacterItem itemMainHand = characterItemResolver.getMainHandWeapon(source);
			skillId = itemMainHand.getItemId();
			if (itemMainHand.getSkillId() != null) {
				skillId = itemMainHand.getSkillId();
			}
		}

		if (context.getAction() instanceof TacticalActionMeleeAttack ma && ma.getMeleeAttackMode() == MeleeAttackMode.TWO_WEAPONS) {
			CharacterItem itemOffHand = characterItemResolver.getOffHandWeapon(source);
			String offHandSkill = itemOffHand.getItemId();
			skillId = skillService.getTwoWeaponSkill(skillId, offHandSkill);
		}
		return Mono.just(context)
			.zipWith(skillService.getSkill(source, skillId), (a, b) -> {
				a.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.SKILL, b);
				a.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.SKILL, b);
				return a;
			});
	}

	private AttackContext loadProcessors(AttackContext context) {
		obProcessors.stream().forEach(processor -> processor.accept(context));
		return context;
	}

	private AttackContext cleanUp(AttackContext context) {
		boolean cleanUp = true;
		TacticalActionAttack action = context.getAction();
		if (action instanceof TacticalActionMeleeAttack meleeAttack && meleeAttack.getMeleeAttackMode() == MeleeAttackMode.TWO_WEAPONS) {
			cleanUp = false;
		}
		if (cleanUp && action.getOffensiveBonusMap().containsKey(AttackTargetType.OFF_HAND)) {
			action.getOffensiveBonusMap().remove(AttackTargetType.OFF_HAND);
		}
		return context;
	}

}
