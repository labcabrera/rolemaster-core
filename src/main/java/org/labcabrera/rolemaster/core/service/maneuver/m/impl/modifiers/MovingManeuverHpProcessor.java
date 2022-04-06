package org.labcabrera.rolemaster.core.service.maneuver.m.impl.modifiers;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier.ModifierType;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.service.maneuver.m.MovingManeuverContext;
import org.labcabrera.rolemaster.core.service.maneuver.m.MovingManeuverModifierProcessor;
import org.springframework.stereotype.Component;

@Component
public class MovingManeuverHpProcessor implements MovingManeuverModifierProcessor {

	@Override
	public void accept(MovingManeuverContext context) {
		if (context.getCharacterInfo().isEmpty() || context.getCharacterStatus().isEmpty()) {
			return;
		}
		Hp hp = context.getCharacterStatus().get().getHp();
		double damagePercent = 100 - hp.getPercent();
		int result;
		if (damagePercent > 75) {
			result = -30;
		}
		else if (damagePercent > 50) {
			result = -20;
		}
		else if (damagePercent > 25) {
			result = -10;
		}
		else {
			result = 0;
		}
		ManeuverModifier modifier = ManeuverModifier.builder()
			.description(String.format("HP maneuver modifier (max hp: %s, current hp: %s, damage percent: %s)",
				hp.getMax(),
				hp.getCurrent(),
				damagePercent))
			.modifier(result)
			.type(ModifierType.HP)
			.build();
		context.getManeuverModifiers().add(modifier);

	}

}
