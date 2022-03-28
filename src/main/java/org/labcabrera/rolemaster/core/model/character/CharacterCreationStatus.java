package org.labcabrera.rolemaster.core.model.character;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CharacterCreationStatus {

	PARTIALLY_CREATED("partiallyCreated"),

	CREATED("created");

	private String name;

	private CharacterCreationStatus(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
