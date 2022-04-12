package org.labcabrera.rolemaster.core.model.tactical;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TacticalRoundState {

	ACTION_DECLARATION("actionDeclaration"),

	INITIATIVE_DECLARATION("actionInitiativeDeclaration"),

	ACTION_RESOLUTION("actionResolution"),

	CLOSED("closed");

	private String name;

	private TacticalRoundState(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
