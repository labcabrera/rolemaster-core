package org.labcabrera.rolemaster.core.model.skill;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum SkillCustomizationRestriction implements CodeNameEnum {

	FREE_TEXT("free-text", "Free text"),

	WEAPON_SKILL("weapon-skill", "Weapon skill"),

	LANGUAGE("language", "Language");

	private String code;

	@Getter
	private String name;

	private SkillCustomizationRestriction(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
