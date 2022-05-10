package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.ob;

import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.service.context.AttackContext;
import org.springframework.stereotype.Component;

@Component
public class OBCustomBonusProcessor implements OBProcessor {

	@Override
	public AttackContext process(AttackContext context) {
		if (context.getAction().getCustomBonus() != null) {
			int bonus = context.getAction().getCustomBonus();
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.CUSTOM, bonus);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.CUSTOM, bonus);
		}
		return context;
	}
}
