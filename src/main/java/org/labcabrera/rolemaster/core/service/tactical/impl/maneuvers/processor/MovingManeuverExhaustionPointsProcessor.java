package org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.service.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.processor.ExhaustionBonusProcessor;
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