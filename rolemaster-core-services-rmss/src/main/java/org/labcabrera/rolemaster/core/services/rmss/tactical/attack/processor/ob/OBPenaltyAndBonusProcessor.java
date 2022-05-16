package org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.ob;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.services.tactical.attack.processor.OBProcessor;
import org.springframework.stereotype.Component;

@Component
public class OBPenaltyAndBonusProcessor implements OBProcessor {

	@Override
	public void accept(AttackContext context) {
		int penalty = context.getSource().getCombatStatus().getTotalPenalty();
		if (penalty != 0) {
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.PENALTY, penalty);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.PENALTY, penalty);
		}
		int bonus = context.getSource().getCombatStatus().getTotalBonus();
		if (bonus != 0) {
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.BONUS, bonus);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.BONUS, bonus);
		}
	}
}
