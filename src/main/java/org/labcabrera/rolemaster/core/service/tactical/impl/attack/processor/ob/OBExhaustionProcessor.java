package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.ob;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.service.context.AttackContext;
import org.springframework.stereotype.Component;

@Component
public class OBExhaustionProcessor implements OBProcessor {

	@Override
	public void accept(AttackContext context) {
		int bonus = getBonusExhaustion(context.getSource());
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.EXHAUSTION, bonus);
		context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.EXHAUSTION, bonus);
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
}
