package org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.ob;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.services.tactical.attack.processor.OBProcessor;
import org.springframework.stereotype.Component;

@Component
public class OBCustomBonusProcessor implements OBProcessor {

	@Override
	public void accept(AttackContext context) {
		if (context.getAction().getCustomBonus() != null) {
			int bonus = context.getAction().getCustomBonus();
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.CUSTOM, bonus);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.CUSTOM, bonus);
		}
	}
}
