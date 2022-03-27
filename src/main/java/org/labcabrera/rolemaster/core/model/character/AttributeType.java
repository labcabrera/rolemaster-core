package org.labcabrera.rolemaster.core.model.character;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AttributeType {

	AGILITY("ag"),

	CONSTITUTION("co"),

	MEMORY("me"),

	REASONING("re"),

	SELF_DISCIPLINE("sd"),

	EMPATHY("em"),

	INTUTITION("in"),

	PRESENCE("pr"),

	QUICKNESS("qu"),

	STRENGTH("st");

	private String name;

	AttributeType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
