package org.labcabrera.rolemaster.core.model.skill;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SkillProgressionType {

	CATEGORY("category"),

	STANDARD("standard"),

	COMBINED("combined"),

	LIMITED("limited"),

	SPECIAL("special"),

	RACE_BODY_DEVELOPMENT("race-body-development"),

	RACE_POWER_POINTS("race-power-points"),

	NONE("none");

	private String name;

	SkillProgressionType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
