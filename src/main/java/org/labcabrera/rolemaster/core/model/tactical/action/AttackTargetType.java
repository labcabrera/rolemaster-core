package org.labcabrera.rolemaster.core.model.tactical.action;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum AttackTargetType implements CodeNameEnum {

	MAIN_HAND("main-hand", "Main Hand"),

	OFF_HAND("off-hand", "Off-Hand");

	private String code;

	@Getter
	private String name;

	private AttackTargetType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
