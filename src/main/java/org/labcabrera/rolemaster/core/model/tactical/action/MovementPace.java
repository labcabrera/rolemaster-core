package org.labcabrera.rolemaster.core.model.tactical.action;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum MovementPace {

	WALK("walk", "Walk", 1.0, ManeuverDificulty.NONE),

	FAST_WALK("fastWalk", "Fast walk", 1.5, ManeuverDificulty.NONE),

	RUN("run", "Run", 1.0, ManeuverDificulty.NONE),

	SPRINT("sprint", "Spring", 1.0, ManeuverDificulty.EASY),

	FAST_SPRINT("fastSprint", "Fast sprint", 1.0, ManeuverDificulty.LIGHT),

	DASH("dash", "Dash", 1.0, ManeuverDificulty.MEDIUM);

	private String name;

	@Getter
	private String description;

	@Getter
	private Double multiplier;

	@Getter
	private ManeuverDificulty dificulty;

	MovementPace(String name, String description, Double multiplier, ManeuverDificulty dificulty) {
		this.name = name;
		this.description = description;
		this.multiplier = multiplier;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
