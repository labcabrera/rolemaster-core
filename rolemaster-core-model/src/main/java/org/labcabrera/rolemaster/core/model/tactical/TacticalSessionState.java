package org.labcabrera.rolemaster.core.model.tactical;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TacticalSessionState {

	CREATED("created"),

	OPEN("open"),

	CLOSED("closed");

	private String name;

	TacticalSessionState(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
