package org.labcabrera.rolemaster.core.model.character;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResistanceBonusType {

	ATTRIBUTE("attribute"),

	RACE("race"),

	SPECIAL("special");

	private String name;

	private ResistanceBonusType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
