package org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.service.context.TacticalActionContext;
import org.springframework.stereotype.Component;

@Component
public class MovingManeuverPenaltyProcessor implements MovingManeuverProcessor {

	@Override
	public void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap) {
		int bonus = context.getSource().getMmBonus();
		bonusMap.put("mm-bonus", bonus);
	}

}
