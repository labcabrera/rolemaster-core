package org.labcabrera.rolemaster.core.model.tactical.actions;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InitiativeModifier {

	RANDOM_ROLL("randomRoll"),

	ATTRIBUTE("attribute"),

	DECLARED_MOVEMENT("declaredMovement"),

	HP("hp"),

	SURPRISED("surprised"),

	CUSTOM("custom");

	private String name;

	private InitiativeModifier(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
