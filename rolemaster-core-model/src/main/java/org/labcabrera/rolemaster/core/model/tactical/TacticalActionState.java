package org.labcabrera.rolemaster.core.model.tactical;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TacticalActionState {

	PENDING("pending"),

	PENDING_FUMBLE_RESOLUTION("pending-fumble-resolution"),

	PENDING_BREAKAGE_RESOLUTION("pending-breakage-resolution"),

	PENDING_CRITICAL_RESOLUTION("pending-critical-resolution"),

	PENDING_RESOLUTION("pending-resolution"),

	RESOLVED("resolved");

	@JsonValue
	String code;

	private TacticalActionState(String code) {
		this.code = code;
	}

}
