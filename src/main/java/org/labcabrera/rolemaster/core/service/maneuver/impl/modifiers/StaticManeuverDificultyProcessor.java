package org.labcabrera.rolemaster.core.service.maneuver.impl.modifiers;

import java.util.HashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverDificulty;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverModifier;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverResult;
import org.labcabrera.rolemaster.core.service.maneuver.impl.StaticManeuverModifierProcessor;
import org.springframework.stereotype.Component;

@Component
public class StaticManeuverDificultyProcessor implements StaticManeuverModifierProcessor {

	private final Map<StaticManeuverDificulty, Integer> modifiers = new HashMap<>();

	public StaticManeuverDificultyProcessor() {
		modifiers.put(StaticManeuverDificulty.ROUTINE, 30);
		modifiers.put(StaticManeuverDificulty.EASY, 20);
		modifiers.put(StaticManeuverDificulty.LIGTH, 10);
		modifiers.put(StaticManeuverDificulty.MEDIUM, 0);
		modifiers.put(StaticManeuverDificulty.HARD, -10);
		modifiers.put(StaticManeuverDificulty.VERY_HARD, -20);
		modifiers.put(StaticManeuverDificulty.EXTREMELY_HARD, -30);
		modifiers.put(StaticManeuverDificulty.SHEER_FOLLY, -50);
		modifiers.put(StaticManeuverDificulty.ABSURD, -70);
	}

	public void accept(StaticManeuverRequest request, StaticManeuverResult result) {
		int mod = modifiers.get(request.getDificulty());
		result.getModifiers().add(StaticManeuverModifier.builder()
			.name("Dificulty modifier")
			.modifier(mod)
			.build());
	}

}
