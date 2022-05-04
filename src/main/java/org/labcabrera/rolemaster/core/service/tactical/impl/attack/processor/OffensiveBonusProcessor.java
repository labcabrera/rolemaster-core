package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.service.context.AttackContext;
import org.labcabrera.rolemaster.core.service.tactical.TacticalSkillService;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class OffensiveBonusProcessor implements AbstractAttackProcessor {

	private static final List<Debuff> NO_DEFENSIVE_BONUS_DEBUFS = Arrays.asList(Debuff.SHOCK, Debuff.PRONE, Debuff.UNCONSCIOUS,
		Debuff.INSTANT_DEATH);

	@Autowired
	private TacticalSkillService skillService;

	@Autowired
	private TacticalCharacterItemResolver characterItemResolver;

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	@Autowired
	private TacticalCharacterItemService itemService;

	@Autowired
	private MissilePreparationServiceBonusProcessor missilePreparationServiceBonusProcessor;

	public Mono<AttackContext> apply(AttackContext context) {
		if (context.getAction().getState() != TacticalActionState.PENDING) {
			return Mono.just(context);
		}
		log.debug("Processing offensive bonus");
		return Mono.just(context)
			.map(this::initialize)
			.flatMap(this::loadSkillBonus)
			.map(this::loadCustomBonus)
			.map(this::loadBonusHp)
			.map(this::loadBonusTargetStatus)
			.map(this::loadBonusExhaustion)
			.map(this::loadBonusActionPercent)
			.map(this::loadBonusMeleePosition)
			.map(this::loadBonusDefensive)
			.map(this::loadBonusParry)
			.map(this::loadBonusPenaltyAndBonus)
			.map(this::loadMissilePreparation)
			.flatMap(this::loadOffHandBonus)
			.flatMap(this::loadBonusDistance)
			.map(this::cleanUp);
	}

	private AttackContext initialize(AttackContext context) {
		context.getAction().getOffensiveBonusMap().put(AttackTargetType.MAIN_HAND, new LinkedHashMap<>());
		context.getAction().getOffensiveBonusMap().put(AttackTargetType.OFF_HAND, new LinkedHashMap<>());
		return context;
	}

	private Mono<AttackContext> loadSkillBonus(AttackContext context) {
		TacticalCharacter source = context.getSource();
		CharacterItem itemMainHand = characterItemResolver.getMainHandWeapon(source);
		String skillId = itemMainHand.getItemId();
		if (itemMainHand.getSkillId() != null) {
			skillId = itemMainHand.getSkillId();
		}
		if (context.getAction() instanceof TacticalActionMeleeAttack ma) {
			if (ma.getMeleeAttackMode() == MeleeAttackMode.TWO_WEAPONS) {
				CharacterItem itemOffHand = characterItemResolver.getOffHandWeapon(source);
				String offHandSkill = itemOffHand.getItemId();
				skillId = skillService.getTwoWeaponSkill(skillId, offHandSkill);
			}
		}
		return Mono.just(context)
			.zipWith(skillService.getSkill(source, skillId), (a, b) -> {
				a.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.SKILL, b);
				a.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.SKILL, b);
				return a;
			});
	}

	private AttackContext loadCustomBonus(AttackContext context) {
		if (context.getAction().getCustomBonus() != null) {
			int bonus = context.getAction().getCustomBonus();
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.CUSTOM, bonus);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.CUSTOM, bonus);
		}
		return context;
	}

	private AttackContext loadBonusDefensive(AttackContext context) {
		context.getTargets().entrySet().stream().forEach(e -> {
			int bd = getBonusDefensive(e.getValue());
			context.getAction().getOffensiveBonusMap().get(e.getKey()).put(OffensiveBonusModifier.DEFENSIVE_BONUS, -bd);
		});
		return context;
	}

	private AttackContext loadBonusPenaltyAndBonus(AttackContext context) {
		int penalty = context.getSource().getCombatStatus().getTotalPenalty();
		if (penalty != 0) {
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.PENALTY, penalty);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.PENALTY, penalty);
		}
		int bonus = context.getSource().getCombatStatus().getTotalBonus();
		if (bonus != 0) {
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.BONUS, bonus);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.BONUS, bonus);
		}
		return context;
	}

	private AttackContext loadBonusHp(AttackContext context) {
		Map<AttackTargetType, Map<OffensiveBonusModifier, Integer>> map = context.getAction().getOffensiveBonusMap();
		map.get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.HP, getBonusHp(context.getSource()));
		map.get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.HP, getBonusHp(context.getSource()));
		return context;
	}

	private AttackContext loadBonusTargetStatus(AttackContext context) {
		context.getTargets().entrySet().stream().forEach(e -> {
			int bonus = getBonusTargetStatus(e.getValue());
			context.getAction().getOffensiveBonusMap().get(e.getKey()).put(OffensiveBonusModifier.TARGET_STATUS, bonus);
		});
		return context;
	}

	private AttackContext loadBonusExhaustion(AttackContext context) {
		int bonus = getBonusExhaustion(context.getSource());
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.EXHAUSTION, bonus);
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.EXHAUSTION, bonus);
		return context;
	}

	private AttackContext loadBonusActionPercent(AttackContext context) {
		int bonus = getBonusActionPercent(context.getAction());
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.ACTION_PERCENT, bonus);
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.ACTION_PERCENT, bonus);
		return context;
	}

	private AttackContext loadBonusParry(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMeleeAttack meleeAttack) {
			int parry = meleeAttack.getParry();
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.PARRY_ATTACK, -parry);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.PARRY_ATTACK, -parry);
		}
		return context;
	}

	private AttackContext loadBonusMeleePosition(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMeleeAttack meleeAttack) {
			context.getTargets().entrySet().stream().forEach(e -> {
				if (meleeAttack.getFacingMap().containsKey(e.getKey())) {
					int bonus = meleeAttack.getFacingMap().get(e.getKey()).getModifier();
					context.getAction().getOffensiveBonusMap().get(e.getKey()).put(OffensiveBonusModifier.MELEE_FACING, bonus);
				}
			});
		}
		return context;
	}

	private Mono<AttackContext> loadBonusDistance(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMissileAttack missileAttack) {
			CharacterItem itemMainHand = itemResolver.getMainHandWeapon(context.getSource());
			Float distance = missileAttack.getDistance();
			int bonus = itemService.getRangeModifier(itemMainHand, distance, context);
			Map<AttackTargetType, Map<OffensiveBonusModifier, Integer>> map = context.getAction().getOffensiveBonusMap();
			map.get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.DISTANCE, bonus);
		}
		return Mono.just(context);
	}

	private Mono<AttackContext> loadOffHandBonus(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMeleeAttack ma) {
			if (ma.getMeleeAttackMode() == MeleeAttackMode.OFF_HAND_WEAPON || ma.getMeleeAttackMode() == MeleeAttackMode.TWO_WEAPONS) {
				//TODO check ambidextrous trait
				context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.OFF_HAND, -20);
			}
		}
		return Mono.just(context);
	}

	private AttackContext loadMissilePreparation(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMissileAttack missileAttack) {
			CharacterItem item = itemResolver.getMainHandWeapon(context.getSource());
			int rounds = missileAttack.getPreparationRounds();
			int value = -missilePreparationServiceBonusProcessor.getPreparationBonus(item.getItemId(), rounds);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND)
				.put(OffensiveBonusModifier.MISSILE_PREPARATION_ROUNDS, value);
		}
		return context;
	}

	private int getBonusDefensive(TacticalCharacter target) {
		for (Debuff debuff : NO_DEFENSIVE_BONUS_DEBUFS) {
			if (target.getCombatStatus().getDebuffs().containsKey(debuff)) {
				return 0;
			}
		}
		return target.getDefensiveBonus();
	}

	private int getBonusHp(TacticalCharacter source) {
		Integer percent = source.getHp().getPercent();
		if (percent < 25) {
			return -30;
		}
		else if (percent < 50) {
			return -20;
		}
		else if (percent < 75) {
			return -10;
		}
		return 0;
	}

	private int getBonusTargetStatus(TacticalCharacter target) {
		int result = 0;
		if (target.getCombatStatus().getDebuffs().containsKey(Debuff.PRONE)) {
			result = 50;
		}
		else if (target.getCombatStatus().getDebuffs().containsKey(Debuff.UNCONSCIOUS)) {
			result = 50;
		}
		else if (target.getCombatStatus().getDebuffs().containsKey(Debuff.DOWNED)) {
			result = 30;
		}
		else if (target.getCombatStatus().getDebuffs().containsKey(Debuff.STUNNED)) {
			result = 20;
		}
		if (target.getCombatStatus().getDebuffs().containsKey(Debuff.SURPRISED)) {
			result += 20;
		}
		return result;
	}

	private int getBonusExhaustion(TacticalCharacter source) {
		Integer percent = source.getExhaustionPoints().getPercent();
		if (percent < 25) {
			return -10;
		}
		else if (percent < 50) {
			return -20;
		}
		else if (percent < 75) {
			return -30;
		}
		else if (percent < 90) {
			return -60;
		}
		else if (percent < 100) {
			return -100;
		}
		return 0;
	}

	private int getBonusActionPercent(TacticalActionAttack action) {
		int percent = action.getActionPercent();
		if (action instanceof TacticalActionMeleeAttack) {
			return percent - 100;
		}
		if (action instanceof TacticalActionMissileAttack) {
			return Integer.min(0, percent - 60);
		}
		return 0;
	}

	private AttackContext cleanUp(AttackContext context) {
		boolean cleanUp = true;
		if (context.getAction() instanceof TacticalActionMeleeAttack meleeAttack) {
			if (meleeAttack.getMeleeAttackMode() == MeleeAttackMode.TWO_WEAPONS) {
				cleanUp = false;
			}
		}
		if (cleanUp && context.getAction().getOffensiveBonusMap().containsKey(AttackTargetType.OFF_HAND)) {
			context.getAction().getOffensiveBonusMap().remove(AttackTargetType.OFF_HAND);
		}
		return context;
	}

}
