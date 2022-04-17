package org.labcabrera.rolemaster.core.model.tactical.action;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum MovementPace implements CodeNameEnum {

	WALK("walk", "Walk", 1.0, ManeuverDificulty.NONE),

	FAST_WALK("fastWalk", "Fast walk", 1.5, ManeuverDificulty.NONE),

	RUN("run", "Run", 1.0, ManeuverDificulty.NONE),

	SPRINT("sprint", "Spring", 1.0, ManeuverDificulty.EASY),

	FAST_SPRINT("fastSprint", "Fast sprint", 1.0, ManeuverDificulty.LIGHT),

	DASH("dash", "Dash", 1.0, ManeuverDificulty.MEDIUM);

	private String code;

	@Getter
	private String name;

	@Getter
	private Double multiplier;

	@Getter
	private ManeuverDificulty dificulty;

	MovementPace(String name, String description, Double multiplier, ManeuverDificulty dificulty) {
		this.code = name;
		this.name = description;
		this.multiplier = multiplier;
		this.dificulty = dificulty;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
