package org.labcabrera.rolemaster.core.model.tactical;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TacticalActionPhase {

	SNAP("snap"),

	NORMAL("normal"),

	DELIBERATE("deliberate");

	private String name;

	private TacticalActionPhase(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
