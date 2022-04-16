package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.labcabrera.rolemaster.core.model.character.inventory.CharacterWeapon;
import org.labcabrera.rolemaster.core.model.item.RangeModifier;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.repository.WeaponRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class OffensiveBonusProcessor {

	private static final List<Debuff> NO_DEFENSIVE_BONUS_DEBUFS = Arrays.asList(Debuff.SHOCK, Debuff.PRONE, Debuff.UNCONSCIOUS);

	@Autowired
	private TacticalSkillService skillService;

	@Autowired
	private WeaponRepository weaponRepository;

	public <T extends AttackContext<?>> Mono<T> apply(T context) {
		if (context.getAction().isFlumbe()) {
			return Mono.just(context);
		}
		return Mono.just(context)
			.flatMap(this::loadSkillBonus)
			.map(this::loadBonusDefensive)
			.map(this::loadBonusHp)
			.map(this::loadBonusTargetStatus)
			.map(this::loadBonusExhaustion)
			.map(this::loadBonusActionPercent)
			.map(this::loadBonusMeleePosition)
			.flatMap(this::loadBonusDistance);
	}

	private <T extends AttackContext<?>> Mono<T> loadSkillBonus(T context) {
		TacticalCharacter source = context.getSource();
		CharacterWeapon weapon = source.getInventory().getMainHandWeapon();
		String skillId = weapon.getSkillId();
		return Mono.just(context)
			.zipWith(skillService.getSkill(source, skillId), (a, b) -> {
				a.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.SKILL, b);
				return a;
			});
	}

	private <T extends AttackContext<?>> T loadBonusDefensive(T context) {
		context.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.DEFENSIVE_BONUS, getBonusDefensive(context.getTarget()));
		return context;
	}

	private <T extends AttackContext<?>> T loadBonusHp(T context) {
		context.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.HP, getBonusHp(context.getSource()));
		return context;
	}

	private <T extends AttackContext<?>> T loadBonusTargetStatus(T context) {
		context.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.TARGET_STATUS, getBonusTargetStatus(context.getTarget()));
		return context;
	}

	private <T extends AttackContext<?>> T loadBonusExhaustion(T context) {
		context.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.EXHAUSTION, getBonusExhaustion(context.getTarget()));
		return context;
	}

	private <T extends AttackContext<?>> T loadBonusActionPercent(T context) {
		context.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.ACTION_PERCENT, getBonusActionPercent(context.getAction()));
		return context;
	}

	private <T extends AttackContext<?>> T loadBonusMeleePosition(T context) {
		Optional<Integer> bonus = getBonusMeleePosition(context);
		if (bonus.isPresent()) {
			context.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.MELEE_FACING, bonus.get());
		}
		return context;
	}

	private <T extends AttackContext<?>> Mono<T> loadBonusDistance(T context) {
		return Mono.just(context)
			.zipWith(getBonusDistance(context))
			.map(pair -> {
				if (pair.getT2().isPresent()) {
					context.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.DISTANCE, pair.getT2().get());
				}
				return pair.getT1();
			});
	}

	private int getBonusDefensive(TacticalCharacter target) {
		for (Debuff debuff : NO_DEFENSIVE_BONUS_DEBUFS) {
			if (target.getCombatStatus().getDebuffs().containsKey(debuff)) {
				return 0;
			}
		}
		return -target.getBaseDefensiveBonus();
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

	private Optional<Integer> getBonusMeleePosition(AttackContext<?> context) {
		if (context.getAction() instanceof TacticalActionMeleeAttack) {
			TacticalActionMeleeAttack action = (TacticalActionMeleeAttack) context.getAction();
			return Optional.of(action.getFacing().getModifier());
		}
		return Optional.empty();
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

	private Mono<Optional<Integer>> getBonusDistance(AttackContext<?> context) {
		if (context.getAction() instanceof TacticalActionMissileAttack) {
			TacticalActionMissileAttack action = (TacticalActionMissileAttack) context.getAction();
			CharacterWeapon mainHandWeapon = context.getSource().getInventory().getMainHandWeapon();
			int distance = action.getDistance();
			return getBonusDistance(distance, mainHandWeapon.getItemId());
		}
		return Mono.just(Optional.empty());
	}

	private Mono<Optional<Integer>> getBonusDistance(Integer distance, String weaponId) {
		return weaponRepository.findById(weaponId)
			.map(weapon -> {
				int modifier = -1000;
				for (RangeModifier rangeModifier : weapon.getRangeModifiers()) {
					if (distance <= rangeModifier.getRange() && rangeModifier.getModifier() > modifier) {
						modifier = rangeModifier.getModifier();
					}
				}
				return Optional.of(modifier);
			});
	}
}
