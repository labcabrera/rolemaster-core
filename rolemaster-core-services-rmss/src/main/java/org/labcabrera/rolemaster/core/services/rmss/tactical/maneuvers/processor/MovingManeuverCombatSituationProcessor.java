package org.labcabrera.rolemaster.core.services.rmss.tactical.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovement;
import org.labcabrera.rolemaster.core.services.tactical.maneuvers.MovingManeuverProcessor;
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
