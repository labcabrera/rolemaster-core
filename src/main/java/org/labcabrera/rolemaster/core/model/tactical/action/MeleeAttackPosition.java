package org.labcabrera.rolemaster.core.model.tactical.action;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MeleeAttackPosition {

	NORMAL("normal"),

	FLANK("flank"),

	REAR("rear");

	String name;

	private MeleeAttackPosition(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
