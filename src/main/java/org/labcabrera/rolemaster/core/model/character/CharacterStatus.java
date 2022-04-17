package org.labcabrera.rolemaster.core.model.character;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum CharacterStatus implements CodeNameEnum {

	PARTIALLY_CREATED("partiallyCreated", "Partially created"),

	CREATED("created", "Created"),

	CLOSED("closed", "Closed"),

	DEATH("death", "Death");

	private String code;

	@Getter
	private String name;

	private CharacterStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
