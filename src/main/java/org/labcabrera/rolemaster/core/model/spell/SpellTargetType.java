package org.labcabrera.rolemaster.core.model.spell;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SpellTargetType {

	TARGET("target"),

	TARGET_LEVEL("targetLvl"),

	RADIUS("radius"),

	AREA("area"),

	CASTER("caster"),

	NA("na"),

	OTHER("other");

	private String name;

	private SpellTargetType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
