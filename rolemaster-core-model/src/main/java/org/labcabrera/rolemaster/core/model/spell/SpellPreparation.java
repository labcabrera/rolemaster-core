package org.labcabrera.rolemaster.core.model.spell;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SpellPreparation {

	I("instant"),

	N("normal");

	private String name;

	private SpellPreparation(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
