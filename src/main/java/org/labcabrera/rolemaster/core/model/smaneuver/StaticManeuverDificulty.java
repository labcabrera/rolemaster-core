package org.labcabrera.rolemaster.core.model.smaneuver;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StaticManeuverDificulty {

	ROUTINE("routine"),

	EASY("easy"),

	LIGTH("light"),

	MEDIUM("medium"),

	HARD("hard"),

	VERY_HARD("very-hard"),

	EXTREMELY_HARD("extremely-hard"),

	SHEER_FOLLY("sheer-folly"),

	ABSURD("absurd");

	private String name;

	private StaticManeuverDificulty(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
