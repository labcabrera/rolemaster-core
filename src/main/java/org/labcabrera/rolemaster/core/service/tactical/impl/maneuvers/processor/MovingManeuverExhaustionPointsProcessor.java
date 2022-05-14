package org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.service.context.TacticalActionContext;
import org.springframework.stereotype.Component;

@Component
class MovingManeuverExhaustionPointsProcessor implements MovingManeuverProcessor {

	public static final String KEY = "exhaustion-points";

	@Override
	public void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap) {
		int percentUsed = 100 - context.getSource().getExhaustionPoints().getPercent();
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
		bonusMap.put(KEY, bonus);
	}

}
