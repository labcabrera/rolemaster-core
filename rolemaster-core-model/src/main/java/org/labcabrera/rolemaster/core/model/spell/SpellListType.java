package org.labcabrera.rolemaster.core.model.spell;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum SpellListType implements CodeNameEnum {

	OPEN("open", "Open"),

	CLOSED("closed", "Closed"),

	BASE("base", "Base"),

	EVIL("evil", "Evil");

	private String code;

	private String name;

	private SpellListType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
