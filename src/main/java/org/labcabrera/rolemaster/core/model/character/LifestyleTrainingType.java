package org.labcabrera.rolemaster.core.model.character;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LifestyleTrainingType {

	LIFESTYLE("lifestyle"),

	VOCATIONAL("vocational");

	private String name;

	private LifestyleTrainingType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
