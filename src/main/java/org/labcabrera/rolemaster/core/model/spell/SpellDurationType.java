package org.labcabrera.rolemaster.core.model.spell;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SpellDurationType {

	TIME("time"),

	TIME_LVL("timeLvl"),

	TIME_FAIL("timeFail"),

	CONCENTRATION("concentration"),

	PERMANENT("permanent"),

	SPECIAL("special"),

	NA("na");

	private String name;

	private SpellDurationType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
