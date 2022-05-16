package org.labcabrera.rolemaster.core.service.tactical.attack.processor.ob;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.service.tactical.attack.processor.OBProcessor;
import org.springframework.stereotype.Component;

@Component
public class OBMeleePositionProcessor implements OBProcessor {

	@Override
	public void accept(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMeleeAttack meleeAttack) {
			context.getTargets().entrySet().stream().forEach(e -> {
				if (meleeAttack.getFacingMap().containsKey(e.getKey())) {
					int bonus = meleeAttack.getFacingMap().get(e.getKey()).getModifier();
					context.getAction().getOffensiveBonusMap().get(e.getKey()).put(OffensiveBonusModifier.MELEE_FACING, bonus);
				}
			});
		}
	}
}
