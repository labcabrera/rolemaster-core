package org.labcabrera.rolemaster.core.model.character;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AttributeBonusType {

	ATTRIBUTE("attribute"),

	RACE("race"),

	SPECIAL("special");

	private String name;

	private AttributeBonusType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
