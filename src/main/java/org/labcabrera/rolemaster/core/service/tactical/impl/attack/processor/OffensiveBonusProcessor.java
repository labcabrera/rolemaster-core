package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.item.RangeModifier;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.repository.WeaponRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalSkillService;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class OffensiveBonusProcessor {

	private static final List<Debuff> NO_DEFENSIVE_BONUS_DEBUFS = Arrays.asList(Debuff.SHOCK, Debuff.PRONE, Debuff.UNCONSCIOUS,
		Debuff.INSTANT_DEATH);

	@Autowired
	private TacticalSkillService skillService;

	@Autowired
	private WeaponRepository weaponRepository;

	@Autowired
	private TacticalCharacterItemResolver characterItemResolver;

	public <T extends AttackContext<?>> Mono<T> apply(T context) {
		if (context.getAction().isFlumbe()) {
			return Mono.just(context);
		}
		return Mono.just(context)
			.map(this::initialize)
			.flatMap(this::loadSkillBonus)
			.map(this::loadBonusHp)
			.map(this::loadBonusTargetStatus)
			.map(this::loadBonusExhaustion)
			.map(this::loadBonusActionPercent)
			.map(this::loadBonusMeleePosition)
			.map(this::loadBonusDefensive)
			.flatMap(this::loadOffHandBonus)
			.flatMap(this::loadBonusDistance);
	}

	private <T extends AttackContext<?>> T initialize(T context) {
		context.getAction().getOffensiveBonusMap().put(AttackTargetType.MAIN_HAND, new LinkedHashMap<>());
		context.getAction().getOffensiveBonusMap().put(AttackTargetType.OFF_HAND, new LinkedHashMap<>());
		return context;
	}

	private <T extends AttackContext<?>> Mono<T> loadSkillBonus(T context) {
		TacticalCharacter source = context.getSource();

		CharacterItem itemMainHand = characterItemResolver.getMainHandWeapon(source);
		String skillId = itemMainHand.getItemId();

		if (context.getAction()instanceof TacticalActionMeleeAttack ma) {
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

	private <T extends AttackContext<?>> T loadBonusDefensive(T context) {
		context.getTargets().entrySet().stream().forEach(e -> {
			int bd = getBonusDefensive(e.getValue());
			context.getAction().getOffensiveBonusMap().get(e.getKey()).put(OffensiveBonusModifier.DEFENSIVE_BONUS, -bd);
		});
		return context;
	}

	private <T extends AttackContext<?>> T loadBonusHp(T context) {
		Map<AttackTargetType, Map<OffensiveBonusModifier, Integer>> map = context.getAction().getOffensiveBonusMap();
		map.get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.HP, getBonusHp(context.getSource()));
		map.get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.HP, getBonusHp(context.getSource()));
		return context;
	}

	private <T extends AttackContext<?>> T loadBonusTargetStatus(T context) {
		context.getTargets().entrySet().stream().forEach(e -> {
			int bonus = getBonusTargetStatus(e.getValue());
			context.getAction().getOffensiveBonusMap().get(e.getKey()).put(OffensiveBonusModifier.TARGET_STATUS, bonus);
		});
		return context;
	}

	private <T extends AttackContext<?>> T loadBonusExhaustion(T context) {
		int bonus = getBonusExhaustion(context.getSource());
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.EXHAUSTION, bonus);
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.EXHAUSTION, bonus);
		return context;
	}

	private <T extends AttackContext<?>> T loadBonusActionPercent(T context) {
		int bonus = getBonusActionPercent(context.getAction());
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.ACTION_PERCENT, bonus);
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.ACTION_PERCENT, bonus);
		return context;
	}

	private <T extends AttackContext<?>> T loadBonusMeleePosition(T context) {
		if (context.getAction()instanceof TacticalActionMeleeAttack meleeAttack) {
			context.getTargets().entrySet().stream().forEach(e -> {
				if (meleeAttack.getFacingMap().containsKey(e.getKey())) {
					int bonus = meleeAttack.getFacingMap().get(e.getKey()).getModifier();
					context.getAction().getOffensiveBonusMap().get(e.getKey()).put(OffensiveBonusModifier.MELEE_FACING, bonus);
				}
			});
		}
		return context;
	}

	private <T extends AttackContext<?>> Mono<T> loadBonusDistance(T context) {
		if (context.getAction()instanceof TacticalActionMissileAttack missileAttack) {
			CharacterItem itemMainHand = context.getSource().getItems().stream()
				.filter(e -> e.getPosition() == ItemPosition.MAIN_HAND)
				.findFirst().orElseThrow(() -> new NotImplementedException("Special attacks not implemented"));
			int distance = missileAttack.getDistance();
			String weaponId = itemMainHand.getItemId();
			return Mono.just(context)
				.zipWith(getBonusDistance(distance, weaponId))
				.map(pair -> {
					int bonus = pair.getT2();
					Map<AttackTargetType, Map<OffensiveBonusModifier, Integer>> map = context.getAction().getOffensiveBonusMap();
					map.get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.DISTANCE, bonus);
					return pair.getT1();
				});
		}
		else {
			return Mono.just(context);
		}
	}

	private <T extends AttackContext<?>> Mono<T> loadOffHandBonus(T context) {
		if (context.getAction()instanceof TacticalActionMeleeAttack ma) {
			if (ma.getMeleeAttackMode() == MeleeAttackMode.OFF_HAND_WEAPON || ma.getMeleeAttackMode() == MeleeAttackMode.TWO_WEAPONS) {
				//TODO check ambidextrous trait
				context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.OFF_HAND, -20);
			}
		}
		return Mono.just(context);

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

	private Mono<Integer> getBonusDistance(Integer distance, String weaponId) {
		return weaponRepository.findById(weaponId)
			.map(weapon -> {
				int modifier = -1000;
				for (RangeModifier rangeModifier : weapon.getRangeModifiers()) {
					if (distance <= rangeModifier.getRange() && rangeModifier.getModifier() > modifier) {
						modifier = rangeModifier.getModifier();
					}
				}
				return modifier;
			});
	}
}
