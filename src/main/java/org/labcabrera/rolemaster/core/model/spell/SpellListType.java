package org.labcabrera.rolemaster.core.model.spell;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SpellListType {

	OPEN("open"),

	CLOSED("closed");

	private String name;

	private SpellListType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
