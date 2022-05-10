package org.labcabrera.rolemaster.core.model.combat;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum CriticalType implements CodeNameEnum {

	S("s", "S"),

	P("p", "P"),

	K("k", "K"),

	B("b", "B"),

	/** Grapple. */
	G("g", "G"),

	HEAT("heat", "Heat"),

	LARGE_CREATURE("large-creature", "Large creature");

	private String code;

	private String name;

	private CriticalType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}
}
