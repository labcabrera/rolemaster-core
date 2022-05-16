package org.labcabrera.rolemaster.core.service.tactical.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.service.tactical.maneuvers.MovingManeuverProcessor;
import org.labcabrera.rolemaster.core.service.tactical.processor.ExhaustionBonusProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class MovingManeuverExhaustionPointsProcessor implements MovingManeuverProcessor {

	public static final String KEY = "exhaustion-points";

	@Autowired
	private ExhaustionBonusProcessor exhaustionBonusProcessor;

	@Override
	public void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap) {
		int bonus = exhaustionBonusProcessor.getBonus(context.getSource().getExhaustionPoints());
		bonusMap.put(KEY, bonus);
	}

}
