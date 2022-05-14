package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.ob;

import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.service.context.AttackContext;
import org.springframework.stereotype.Component;

@Component
public class OBActionPercentProcessor implements OBProcessor {

	@Override
	public void accept(AttackContext context) {
		int bonus = getBonusActionPercent(context.getAction());
		if (isPressAndMeleeAttack(context)) {
			bonus = bonus / 2;
		}
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.ACTION_PERCENT, bonus);
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.ACTION_PERCENT, bonus);
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

	private boolean isPressAndMeleeAttack(AttackContext context) {
		return context.getAction() instanceof TacticalActionMeleeAttack meleeAttack
			&& meleeAttack.getMeleeAttackType() == MeleeAttackType.PRESS_AND_MELEE;
	}

}
