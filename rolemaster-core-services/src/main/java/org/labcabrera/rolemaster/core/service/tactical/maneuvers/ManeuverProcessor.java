package org.labcabrera.rolemaster.core.service.tactical.maneuvers;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;

public interface ManeuverProcessor {

	void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap);

}
