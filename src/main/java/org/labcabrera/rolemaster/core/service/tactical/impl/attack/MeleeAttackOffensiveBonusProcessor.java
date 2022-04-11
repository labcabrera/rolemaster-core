package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import java.util.function.UnaryOperator;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springframework.stereotype.Component;

@Component
public class MeleeAttackOffensiveBonusProcessor implements UnaryOperator<MeleeAttackContext> {

	@Override
	public MeleeAttackContext apply(MeleeAttackContext context) {
		TacticalCharacter source = context.getSource();

		Integer skillBonus = source.getAttack().getMainWeaponBonus();
		Integer flankBonus = getFlankBonus(context.getExecution());
		Integer hpBonus = getHpBonus(source);

		context.getAction().getOffensiveBonusMap().put("skill", skillBonus);
		context.getAction().getOffensiveBonusMap().put("flank", flankBonus);
		context.getAction().getOffensiveBonusMap().put("hp", hpBonus);

		return context;
	}

	private Integer getFlankBonus(MeleeAttackExecution execution) {
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

	private Integer getHpBonus(TacticalCharacter source) {
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

}
