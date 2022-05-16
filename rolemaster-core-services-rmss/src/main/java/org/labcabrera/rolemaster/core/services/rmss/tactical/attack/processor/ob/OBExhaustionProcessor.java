package org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.ob;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.services.rmss.tactical.processor.ExhaustionBonusProcessor;
import org.labcabrera.rolemaster.core.services.tactical.attack.processor.OBProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OBExhaustionProcessor implements OBProcessor {

	@Autowired
	private ExhaustionBonusProcessor exhaustionBonusProcessor;

	@Override
	public void accept(AttackContext context) {
		int bonus = exhaustionBonusProcessor.getBonus(context.getSource().getExhaustionPoints());
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.EXHAUSTION, bonus);
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.EXHAUSTION, bonus);
	}

}
