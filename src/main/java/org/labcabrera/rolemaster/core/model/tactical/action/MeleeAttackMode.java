package org.labcabrera.rolemaster.core.model.tactical.action;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum MeleeAttackMode implements CodeNameEnum {

	MAIN_WEAPON("main-hand-weapon", "Main Hand Weapon"),

	OFF_HAND_WEAPON("off-hand-weapon", "Off-hand Weapon"),

	TWO_WEAPONS("two-weapons", "Two weapons"),

	PARRY("parry", "Parry");

	private String code;

	@Getter
	private String name;

	private MeleeAttackMode(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
