package org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.service.context.TacticalActionContext;

public interface ManeuverProcessor {

	void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap);

}
