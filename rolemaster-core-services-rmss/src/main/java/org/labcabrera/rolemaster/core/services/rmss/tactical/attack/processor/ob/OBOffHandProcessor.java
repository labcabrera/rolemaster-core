package org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.ob;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.services.tactical.attack.processor.OBProcessor;
import org.springframework.stereotype.Component;

@Component
public class OBOffHandProcessor implements OBProcessor {

	@Override
	public void accept(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMeleeAttack ma
			&& (ma.getMeleeAttackMode() == MeleeAttackMode.OFF_HAND_WEAPON || ma.getMeleeAttackMode() == MeleeAttackMode.TWO_WEAPONS)) {
			boolean ambidexterity = false;
			if (context.getSourceCharacterInfo() != null) {
				ambidexterity = context.getSourceCharacterInfo().getCustomizations()
					.stream().filter(e -> e.startsWith("ambidexterity"))
					.count() > 0;
			}
			if (!ambidexterity) {
				context.getAction().getOffensiveBonusMap().get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.OFF_HAND, -20);
			}
		}
	}
}
