package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import java.util.function.UnaryOperator;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.tactical.DebufStatus;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springframework.stereotype.Component;

@Component
public class MeleeAttackOffensiveBonusProcessor implements UnaryOperator<MeleeAttackContext> {

	@Override
	public MeleeAttackContext apply(MeleeAttackContext context) {
		TacticalCharacter source = context.getSource();
		TacticalCharacter target = context.getSource();

		int skillBonus = source.getAttack().getMainWeaponBonus();
		int flankBonus = getFlankBonus(context.getExecution());
		int hpBonus = getHpBonus(source);
		int exhaustionBonus = getExhaustionBonus(source);
		int targetStatus = getTargetStatusBonus(target);

		context.getAction().getOffensiveBonusMap().put("skill", skillBonus);
		context.getAction().getOffensiveBonusMap().put("flank", flankBonus);
		context.getAction().getOffensiveBonusMap().put("hp", hpBonus);
		context.getAction().getOffensiveBonusMap().put("exhaustion", exhaustionBonus);
		context.getAction().getOffensiveBonusMap().put("targetStatus", targetStatus);

		return context;
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
		if (target.getCombatStatus().getDebufStatusMap().containsKey(DebufStatus.PRONE)) {
			result = 50;
		}
		else if (target.getCombatStatus().getDebufStatusMap().containsKey(DebufStatus.UNCONSCIOUS)) {
			result = 50;
		}
		else if (target.getCombatStatus().getDebufStatusMap().containsKey(DebufStatus.DOWNED)) {
			result = 30;
		}
		else if (target.getCombatStatus().getDebufStatusMap().containsKey(DebufStatus.STUNNED)) {
			result = 20;
		}

		if (target.getCombatStatus().getDebufStatusMap().containsKey(DebufStatus.SURPRISED)) {
			result += 20;
		}
		return result;
	}

}