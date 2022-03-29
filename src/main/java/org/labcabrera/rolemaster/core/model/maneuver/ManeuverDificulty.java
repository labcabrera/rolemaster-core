package org.labcabrera.rolemaster.core.model.maneuver;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ManeuverDificulty {

	NONE("none"),

	ROUTINE("routine"),

	EASY("easy"),

	LIGHT("light"),

	MEDIUM("medium"),

	HARD("hard"),

	VERY_HARD("very-hard"),

	EXTREMELY_HARD("extremely-hard"),

	SHEER_FOLLY("sheer-folly"),

	ABSURD("absurd");

	private String name;

	private ManeuverDificulty(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
