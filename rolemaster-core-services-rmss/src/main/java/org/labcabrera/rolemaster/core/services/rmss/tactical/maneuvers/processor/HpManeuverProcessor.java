package org.labcabrera.rolemaster.core.services.rmss.tactical.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.services.tactical.maneuvers.ManeuverProcessor;
import org.springframework.stereotype.Component;

@Component
class HpManeuverProcessor implements ManeuverProcessor {

	public static final String KEY = "hp";

	@Override
	public void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap) {
		int percentTaken = 100 - context.getSource().getHp().getPercent();
		int bonus;
		if (percentTaken <= 25) {
			bonus = 0;
		}
		else if (percentTaken <= 50) {
			bonus = -10;
		}
		else if (percentTaken <= 75) {
			bonus = -20;
		}
		else {
			bonus = -30;
		}
		bonusMap.put(KEY, bonus);
	}

}
