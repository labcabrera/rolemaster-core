package org.labcabrera.rolemaster.core.model.character;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum AttributeBonusType implements CodeNameEnum {

	ATTRIBUTE("attribute", "Attribute"),

	RACE("race", "Race"),

	SPECIAL("special", "Special");

	private String code;

	@Getter
	private String name;

	private AttributeBonusType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}
}
