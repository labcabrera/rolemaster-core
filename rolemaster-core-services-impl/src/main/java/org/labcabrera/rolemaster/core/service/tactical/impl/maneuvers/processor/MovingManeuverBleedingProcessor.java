package org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.service.context.TacticalActionContext;
import org.springframework.stereotype.Component;

@Component
public class MovingManeuverBleedingProcessor implements MovingManeuverProcessor {

	@Override
	public void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap) {
		int bleeding = context.getSource().getCombatStatus().getTotalBleeding();
		if (bleeding > 0) {
			bonusMap.put("bleeding", -5 * bleeding);
		}
	}

}
