package org.labcabrera.rolemaster.core.model.tactical.actions;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MeleeFlankType {

	NONE("none"),

	FLANK("flank"),

	BACK("back");

	String name;

	private MeleeFlankType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
