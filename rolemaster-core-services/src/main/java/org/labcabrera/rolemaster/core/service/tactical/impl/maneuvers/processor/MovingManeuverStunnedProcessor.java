package org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers.processor;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.service.context.TacticalActionContext;
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
