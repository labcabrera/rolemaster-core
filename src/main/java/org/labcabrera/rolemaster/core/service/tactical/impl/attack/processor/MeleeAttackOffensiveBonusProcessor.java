package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.service.tactical.TacticalSkillService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.TacticalAttackContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class MeleeAttackOffensiveBonusProcessor {

	@Autowired
	private TacticalSkillService skillService;

	public Mono<TacticalAttackContext> apply(TacticalAttackContext context) {
		if (context.getAction().isFlumbe()) {
			return Mono.just(context);
		}
		TacticalCharacter source = context.getSource();
		TacticalCharacter target = context.getSource();
		String skillId = source.getInventory().getMainHandWeapon().getSkillId();

		Mono
			.just(context)
			.zipWith(skillService.getSkill(target, skillId));

		int skillBonus = 40; //source.getItems().getMainWeaponBonus();
		int flankBonus = getFlankBonus(context.getExecution());
		int hpBonus = getHpBonus(source);
		int exhaustionBonus = getExhaustionBonus(source);
		int targetStatus = getTargetStatusBonus(target);

		context.getAction().getOffensiveBonusMap().put("skill", skillBonus);
		context.getAction().getOffensiveBonusMap().put("flank", flankBonus);
		context.getAction().getOffensiveBonusMap().put("hp", hpBonus);
		context.getAction().getOffensiveBonusMap().put("exhaustion", exhaustionBonus);
		context.getAction().getOffensiveBonusMap().put("targetStatus", targetStatus);

		return Mono.just(context);
	}

	private int getFlankBonus(MeleeAttackExecution execution) {
		switch (execution.getPosition()) {
		case FLANK:
			return 15;
		case REAR:
			return 35;
		case NORMAL:
		default:
			return 0;
		}
	}

	private int getHpBonus(TacticalCharacter source) {
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

	private int getExhaustionBonus(TacticalCharacter source) {
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

	private int getTargetStatusBonus(TacticalCharacter target) {
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

}
