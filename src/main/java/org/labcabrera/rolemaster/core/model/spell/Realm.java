package org.labcabrera.rolemaster.core.model.spell;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum Realm {

	CHANNELING("channeling", "Channeling"),

	ESSENCE("essence", "Essence"),

	MENTALISM("mentalism", "Mentalism"),

	ARCANE("arcane", "Arcane");

	private String name;

	@Getter
	private String description;

	Realm(String name, String description) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
