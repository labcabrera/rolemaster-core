package org.labcabrera.rolemaster.core.model.tactical.action;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum FumbleType implements CodeNameEnum {

	WEAPON_1H("weapon-1h", "One-Handed arms"),

	WEAPON_2H("weapon-2h", "Two-Handed arms"),

	MISSILE("missile", "Missigle weapons"),

	POLE_ARMS("pole-arms", "Polearms and spears"),

	THROWN("thrown", "Thrown arms"),

	MOUNTED("mounted", "Mounted arms");

	private String code;

	@Getter
	private String name;

	private FumbleType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
