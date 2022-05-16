package org.labcabrera.rolemaster.core.services.rmss.tactical.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.services.tactical.maneuvers.MovingManeuverProcessor;
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
