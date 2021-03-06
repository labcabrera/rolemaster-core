package org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.ob;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.services.tactical.attack.processor.OBProcessor;
import org.springframework.stereotype.Component;

@Component
public class OBMeleeTypeProcessor implements OBProcessor {

	@Override
	public void accept(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMeleeAttack meleeAttack) {
			switch (meleeAttack.getMeleeAttackType()) {
			case FULL:
				addBonus(context, 10);
				break;
			case REACT_AND_MELEE:
				addBonus(context, -10);
				break;
			default:
				break;
			}
		}
	}

	private void addBonus(AttackContext context, int bonus) {
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.MELEE_TYPE, bonus);
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.MELEE_TYPE, bonus);
	}
}
