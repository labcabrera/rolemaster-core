package org.labcabrera.rolemaster.core.model.tactical;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum AttackType implements CodeNameEnum {

	MELEE("melee", "Melee"),

	MISSILE("missile", "Missile");

	private String code;

	@Getter
	private String name;

	private AttackType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
