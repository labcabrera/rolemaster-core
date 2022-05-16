package org.labcabrera.rolemaster.core.service.tactical.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.service.tactical.maneuvers.ManeuverProcessor;
import org.springframework.stereotype.Component;

@Component
public class MovingManeuverWeigthProcessor implements ManeuverProcessor {

	@Override
	public void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap) {
		//TODO
	}

}
