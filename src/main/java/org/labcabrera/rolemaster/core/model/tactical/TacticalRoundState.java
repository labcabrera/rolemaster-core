package org.labcabrera.rolemaster.core.model.tactical;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TacticalRoundState {

	ACTION_DECLARATION("action-declaration"),

	INITIATIVE_DECLARATION("initiative-declaration"),

	ACTION_RESOLUTION("action-resolution"),

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
