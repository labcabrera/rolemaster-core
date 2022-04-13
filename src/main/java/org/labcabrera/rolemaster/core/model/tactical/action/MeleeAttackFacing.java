package org.labcabrera.rolemaster.core.model.tactical.action;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MeleeAttackFacing {

	NORMAL("normal"),

	FLANK("flank"),

	REAR_FLANK("rearFlank"),

	REAR("rear");

	String name;

	private MeleeAttackFacing(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
