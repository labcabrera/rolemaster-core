package org.labcabrera.rolemaster.core.services.rmss.tactical.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.services.tactical.maneuvers.ManeuverProcessor;
import org.springframework.stereotype.Component;

@Component
public class MovingManeuverWeigthProcessor implements ManeuverProcessor {

	@Override
	public void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap) {
		//TODO
	}

}
