package org.labcabrera.rolemaster.core.model.skill;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SkillType {

	MOVING_MANEUVER("movingManeuver"),

	STATIC_MANEUVER("staticManeuver"),

	SPECIAL("special"),

	STATIC_OR_MOVING_MANEUVER("staticOrMovingManeuver");

	private String name;

	private SkillType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
