package org.labcabrera.rolemaster.core.model.tactical.action;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum MovementPace implements CodeNameEnum {

	WALK("walk", "Walk", 1.0, ManeuverDifficulty.NONE),

	FAST_WALK("fastWalk", "Fast walk", 1.5, ManeuverDifficulty.NONE),

	RUN("run", "Run", 1.0, ManeuverDifficulty.NONE),

	SPRINT("sprint", "Spring", 1.0, ManeuverDifficulty.EASY),

	FAST_SPRINT("fastSprint", "Fast sprint", 1.0, ManeuverDifficulty.LIGHT),

	DASH("dash", "Dash", 1.0, ManeuverDifficulty.MEDIUM);

	private String code;

	@Getter
	private String name;

	@Getter
	private Double multiplier;

	@Getter
	private ManeuverDifficulty difficulty;

	MovementPace(String name, String description, Double multiplier, ManeuverDifficulty difficulty) {
		this.code = name;
		this.name = description;
		this.multiplier = multiplier;
		this.difficulty = difficulty;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
