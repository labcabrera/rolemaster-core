package org.labcabrera.rolemaster.core.service.tactical.attack.processor.ob;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.service.tactical.attack.processor.OBProcessor;
import org.springframework.stereotype.Component;

@Component
public class OBParryProcessor implements OBProcessor {

	@Override
	public void accept(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMeleeAttack meleeAttack) {
			int parry = meleeAttack.getParry();
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.PARRY_ATTACK, -parry);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.PARRY_ATTACK, -parry);
		}
	}

}
