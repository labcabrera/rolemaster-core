package org.labcabrera.rolemaster.core.service.tactical.attack.processor.ob;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.service.tactical.attack.processor.OBProcessor;
import org.springframework.stereotype.Component;

@Component
public class OBPriorityProcessor implements OBProcessor {

	@Override
	public void accept(AttackContext context) {
		switch (context.getAction().getPriority()) {
		case SNAP:
			setBonus(context, -20);
			break;
		case DELIBERATE:
			setBonus(context, 10);
			break;
		default:
			break;
		}
	}

	private void setBonus(AttackContext context, int bonus) {
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.PRIORITY, bonus);
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.PRIORITY, bonus);
	}
}
