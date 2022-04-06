package org.labcabrera.rolemaster.core.model.tactical;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TacticalActionState {

	QUEUED("queued"),

	RESOLVED("resolved");

	@JsonValue
	String name;

	private TacticalActionState(String name) {
		this.name = name;
	}

}
