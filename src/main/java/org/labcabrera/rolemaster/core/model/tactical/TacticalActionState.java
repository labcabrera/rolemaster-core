package org.labcabrera.rolemaster.core.model.tactical;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TacticalActionState {

	PENDING("pending"),

	PENDING_CRITICAL_RESOLUTION("pending-critical-resolution"),

	PENDING_FUMBLE_RESOLUTION("pending-fumble-resolution"),

	PENDING_RESOLUTION("pending-resolution"),

	RESOLVED("resolved");

	@JsonValue
	String name;

	private TacticalActionState(String name) {
		this.name = name;
	}

}
