package org.labcabrera.rolemaster.core.model.character;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public enum BonusType {

	RANK("rank"),

	CATEGORY("category"),

	ATTRIBUTE("attribute"),

	PROFESSION("profession"),

	RACE("race"),

	SPECIAL("special"),

	ITEM("item");

	private String name;

	private BonusType(String name) {
		this.name = name;
	}

	@JsonSerialize
	public String getName() {
		return name;
	}

}
