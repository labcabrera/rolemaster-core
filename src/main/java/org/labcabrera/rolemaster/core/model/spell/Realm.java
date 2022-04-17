package org.labcabrera.rolemaster.core.model.spell;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum Realm implements CodeNameEnum {

	CHANNELING("channeling", "Channeling"),

	ESSENCE("essence", "Essence"),

	MENTALISM("mentalism", "Mentalism"),

	ARCANE("arcane", "Arcane");

	private String code;

	@Getter
	private String name;

	Realm(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
