package org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovement;
import org.labcabrera.rolemaster.core.service.context.TacticalActionContext;
import org.springframework.stereotype.Component;

@Component
public class MovingManeuverCombatSituationProcessor implements MovingManeuverProcessor {

	@Override
	public void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap) {
		TacticalAction action = context.getAction();
		if (action instanceof TacticalActionMovement movement) {
			int bonus = movement.getCombatSituation().getBonus();
			bonusMap.put("combat-situation", bonus);
		}
	}

}
