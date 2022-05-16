package org.labcabrera.rolemaster.core.model.spell;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SpellRangeType {

	SELF("self"),

	TOUCH("touch"),

	DISTANCE("distance"),

	DISTANCE_LVL("distanceLvl"),

	UNLIMITED("unlimited"),

	VARIES("varies");

	private String name;

	private SpellRangeType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
