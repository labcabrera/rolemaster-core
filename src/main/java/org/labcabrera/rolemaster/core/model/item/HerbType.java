package org.labcabrera.rolemaster.core.model.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum HerbType implements CodeNameEnum {

	ANTIDOTE("antidote", "Antidote"),

	BONE_REPAIR("bone-repair", "Bone repair");

	private HerbType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private String code;

	private String name;

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
