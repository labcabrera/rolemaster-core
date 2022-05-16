package org.labcabrera.rolemaster.core.services.rmss.tactical.processor;

import org.labcabrera.rolemaster.core.model.tactical.ExhaustionPoints;
import org.springframework.stereotype.Component;

@Component
public class ExhaustionBonusProcessor {

	public int getBonus(ExhaustionPoints exhaustionPoints) {
		int percentUsed = 100 - exhaustionPoints.getPercent();
		int bonus;
		if (percentUsed <= 25) {
			bonus = 0;
		}
		else if (percentUsed <= 50) {
			bonus = -5;
		}
		else if (percentUsed <= 75) {
			bonus = -15;
		}
		else if (percentUsed <= 90) {
			bonus = -30;
		}
		else if (percentUsed <= 99) {
			bonus = -60;
		}
		else {
			bonus = -100;
		}
		return bonus;
	}
}
