package org.labcabrera.rolemaster.core.services.rmss.tactical.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.services.tactical.maneuvers.MovingManeuverProcessor;
import org.springframework.stereotype.Component;

@Component
public class MovingManeuverStunnedProcessor implements MovingManeuverProcessor {

	@Override
	public void loadBonus(TacticalActionContext<?> context, Map<String, Integer> bonusMap) {
		if (context.getSource().getCombatStatus().getDebuffs().containsKey(Debuff.STUNNED)) {
			//TODO read sd bonus
			int sdBonus = 0;
			int modifier = -50 + 3 * sdBonus;
			bonusMap.put("stunned", modifier);
		}
	}

}
