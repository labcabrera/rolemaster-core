package org.labcabrera.rolemaster.core.model.tactical;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TacticalActionState {

	PENDING("pending"),

	PENDING_CRITICAL_RESOLUTION("pendingCriticalResolution"),

	PENDING_RESOLUTION("pendingResolution"),

	RESOLVED("resolved");

	@JsonValue
	String name;

	private TacticalActionState(String name) {
		this.name = name;
	}

}
