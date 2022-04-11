package org.labcabrera.rolemaster.core.model.tactical;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DebufStatus {

	// Aturdido
	STUNNED("stunned"),

	// Caido
	DOWNED("downed"),

	// Derribado
	PRONE("prone"),

	SURPRISED("surprised"),

	UNCONSCIOUS("unconscious"),

	CANT_PARRY("cantParry"),

	MUST_PARRY("mustParry");

	String name;

	private DebufStatus(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
