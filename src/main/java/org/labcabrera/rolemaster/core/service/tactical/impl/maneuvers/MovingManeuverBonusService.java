package org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers;

import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.service.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers.processor.MovingManeuverProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovingManeuverBonusService {

	@Autowired
	private List<MovingManeuverProcessor> processors;

	public void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap) {
		processors.stream().forEach(e -> e.loadBonus(context, bonusMap));
	}

}
