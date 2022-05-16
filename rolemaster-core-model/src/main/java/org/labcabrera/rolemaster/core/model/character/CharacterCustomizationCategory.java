package org.labcabrera.rolemaster.core.model.character;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum CharacterCustomizationCategory implements CodeNameEnum {

	SPECIAL_TRAINING("special-training", "Special training"),

	PHYSICAL("physical", "Physical"),

	MYSTICAL("mystical", "Mystical"),

	MENTAL("mental", "Mental"),

	SPECIAL("special", "Special"),

	SPECIAL_STATUS("special-status", "Special status"),

	SPECIAL_ITEMS("special-items", "Special items");

	private String code;

	@Getter
	private String name;

	private CharacterCustomizationCategory(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
