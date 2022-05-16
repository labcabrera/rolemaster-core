package org.labcabrera.rolemaster.core.model.tactical.action;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OffHandUsage {

	NONE("none"),

	ATTACK("attack"),

	PARRY("parry");

	private String name;

	private OffHandUsage(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
