package org.labcabrera.rolemaster.core.model.character;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum BonusType implements CodeNameEnum {

	RANK("rank", "Rank"),

	CATEGORY("category", "Category"),

	ATTRIBUTE("attribute", "Attribute"),

	PROFESSION("profession", "Profession"),

	RACE("race", "Race"),

	SPECIAL("special", "Special"),

	ITEM("item", "Item"),

	SKILL_SPECIAL("skillSpecial", "Skill special");

	private String code;

	@Getter
	private String name;

	private BonusType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
