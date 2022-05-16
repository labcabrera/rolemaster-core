package org.labcabrera.rolemaster.core.model.skill;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum SkillType implements CodeNameEnum {

	MOVING_MANEUVER("moving", "Moving manevuer"),

	STATIC_MANEUVER("static", "Static maneuver"),

	SPECIAL("special", "Special"),

	STATIC_OR_MOVING_MANEUVER("static-or-moving", "Static or moving maneuver"),

	ARMOR("armor", "Armor"),

	OFFENSIVE_BONUS("offensive-bonus", "Offensive bonus");

	private String code;

	private String name;

	private SkillType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}
}
