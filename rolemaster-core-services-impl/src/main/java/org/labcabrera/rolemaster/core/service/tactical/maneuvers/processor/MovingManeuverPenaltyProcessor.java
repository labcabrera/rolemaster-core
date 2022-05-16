package org.labcabrera.rolemaster.core.service.tactical.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.service.tactical.maneuvers.MovingManeuverProcessor;
import org.springframework.stereotype.Component;

@Component
public class MovingManeuverPenaltyProcessor implements MovingManeuverProcessor {

	@Override
	public void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap) {
		int bonus = context.getSource().getMmBonus();
		bonusMap.put("mm-bonus", bonus);
	}

}
