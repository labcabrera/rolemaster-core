package org.labcabrera.rolemaster.core.model.character;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum SpecialAttackSize implements CodeNameEnum {

	TINY("tiny", "Tiny"),

	SMALL("small", "Small"),

	MEDIUM("medium", "Medium"),

	LARGE("large", "Large"),

	HUGE("huge", "Huge");

	private String code;

	private String name;

	private SpecialAttackSize(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
