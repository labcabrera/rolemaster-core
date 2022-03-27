package org.labcabrera.rolemaster.core.service.maneuver.m.impl.modifiers;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier.ModifierType;
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
		Integer maxHp = context.getCharacterInfo().get().getMaxHp();
		if (maxHp == null) {
			throw new IllegalArgumentException("Undefined character max HP");
		}
		Integer currentHp = context.getCharacterStatus().get().getHp();
		if (currentHp == null) {
			throw new IllegalArgumentException("Undefined current HP");
		}
		double damage = maxHp - currentHp;
		double damagePercent = 100 * (damage / (double) maxHp);
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
				maxHp,
				currentHp,
				damagePercent))
			.modifier(result)
			.type(ModifierType.HP)
			.build();
		context.getManeuverModifiers().add(modifier);

	}

}
