package org.labcabrera.rolemaster.core.model.character;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BonusType {

	RANK("rank"),

	CATEGORY("category"),

	ATTRIBUTE("attribute"),

	PROFESSION("profession"),

	RACE("race"),

	SPECIAL("special"),

	ITEM("item"),

	SKILL_SPECIAL("skill-special");

	private String name;

	private BonusType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
