package org.labcabrera.rolemaster.core.model.session;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TacticalSessionState {

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
