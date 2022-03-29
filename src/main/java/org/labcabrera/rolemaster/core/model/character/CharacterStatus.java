package org.labcabrera.rolemaster.core.model.character;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CharacterStatus {

	PARTIALLY_CREATED("partiallyCreated"),

	CREATED("created"),

	CLOSED("closed"),

	DEATH("death");

	private String name;

	private CharacterStatus(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
