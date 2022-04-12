package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeAttackPosition;
import org.springframework.stereotype.Component;

/**
 * Only melee attacks.
 */
@Component
public class OffensivePositionBonusCalculator {

	public Integer getFlankBonus(MeleeAttackPosition position) {
		switch (position) {
		case FLANK:
			return 15;
		case REAR:
			return 35;
		case NORMAL:
		default:
			return 0;
		}
	}
}
