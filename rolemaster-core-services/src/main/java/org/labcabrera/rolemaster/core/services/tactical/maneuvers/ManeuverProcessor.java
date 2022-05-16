package org.labcabrera.rolemaster.core.services.tactical.maneuvers;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;

public interface ManeuverProcessor {

	void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap);

}
