package org.labcabrera.rolemaster.core.model.character;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum CharacterCustomizationCostType implements CodeNameEnum {

	LESSER("lesser", "Lesser"),

	MINOR("minor", "Minor"),

	MAJOR("major", "Major"),

	GREATER("greater", "greater");

	private String code;

	@Getter
	private String name;

	private CharacterCustomizationCostType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
