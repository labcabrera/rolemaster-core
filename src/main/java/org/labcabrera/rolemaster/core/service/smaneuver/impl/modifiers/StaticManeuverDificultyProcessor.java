package org.labcabrera.rolemaster.core.service.smaneuver.impl.modifiers;

import java.util.HashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.smaneuver.StaticManeuverContext;
import org.labcabrera.rolemaster.core.model.smaneuver.StaticManeuverDificulty;
import org.labcabrera.rolemaster.core.model.smaneuver.StaticManeuverModifier;
import org.labcabrera.rolemaster.core.service.smaneuver.impl.StaticManeuverModifierProcessor;
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

	public void accept(StaticManeuverContext context) {
		int mod = modifiers.get(context.getRequest().getDificulty());
		StaticManeuverModifier modifier = StaticManeuverModifier.builder()
			.name("Dificulty modifier")
			.modifier(mod)
			.build();
		context.getModifiers().add(modifier);
	}

}
