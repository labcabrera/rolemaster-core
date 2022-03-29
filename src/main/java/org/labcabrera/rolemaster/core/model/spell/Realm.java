package org.labcabrera.rolemaster.core.model.spell;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Realm {

	CHANNELING("channeling"),

	ESSENCE("essence"),

	MENTALISM("mentalism");

	private String name;

	Realm(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
