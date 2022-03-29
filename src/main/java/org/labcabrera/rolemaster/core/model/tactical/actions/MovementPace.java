package org.labcabrera.rolemaster.core.model.tactical.actions;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum MovementPace {

	WALK("walk", 1.0, ManeuverDificulty.NONE),

	FAST_WALK("walk", 1.5, ManeuverDificulty.NONE),

	RUN("walk", 1.0, ManeuverDificulty.NONE),

	SPRINT("walk", 1.0, ManeuverDificulty.EASY),

	FAST_SPRINT("walk", 1.0, ManeuverDificulty.LIGHT),

	DASH("walk", 1.0, ManeuverDificulty.MEDIUM);

	private String name;

	@Getter
	private Double multiplier;

	@Getter
	private ManeuverDificulty dificulty;

	MovementPace(String name, Double multiplier, ManeuverDificulty dificulty) {
		this.name = name;
		this.multiplier = multiplier;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
