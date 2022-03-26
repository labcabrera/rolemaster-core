package org.labcabrera.rolemaster.core.service.maneuver.s.impl.modifiers;

import java.util.EnumMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier.ModifierType;
import org.labcabrera.rolemaster.core.model.maneuver.s.StaticManeuverContext;
import org.labcabrera.rolemaster.core.service.maneuver.s.impl.StaticManeuverModifierProcessor;
import org.springframework.stereotype.Component;

@Component
public class StaticManeuverDificultyProcessor implements StaticManeuverModifierProcessor {

	private final Map<ManeuverDificulty, Integer> modifiers = new EnumMap<>(ManeuverDificulty.class);

	public StaticManeuverDificultyProcessor() {
		modifiers.put(ManeuverDificulty.ROUTINE, 30);
		modifiers.put(ManeuverDificulty.EASY, 20);
		modifiers.put(ManeuverDificulty.LIGTH, 10);
		modifiers.put(ManeuverDificulty.MEDIUM, 0);
		modifiers.put(ManeuverDificulty.HARD, -10);
		modifiers.put(ManeuverDificulty.VERY_HARD, -20);
		modifiers.put(ManeuverDificulty.EXTREMELY_HARD, -30);
		modifiers.put(ManeuverDificulty.SHEER_FOLLY, -50);
		modifiers.put(ManeuverDificulty.ABSURD, -70);
	}

	public void accept(StaticManeuverContext context) {
		int mod = modifiers.get(context.getRequest().getDificulty());
		ManeuverModifier modifier = ManeuverModifier.builder()
			.description("Dificulty modifier")
			.modifier(mod)
			.type(ModifierType.DIFICULTY)
			.build();
		context.getModifiers().add(modifier);
	}

}
