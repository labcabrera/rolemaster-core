package org.labcabrera.rolemaster.core.model.skill;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SkillBonusType {

	STANDARD("standard"),

	COMBINED("combined"),

	LIMITED("limited"),

	SPECIAL("special");

	private String name;

	SkillBonusType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
