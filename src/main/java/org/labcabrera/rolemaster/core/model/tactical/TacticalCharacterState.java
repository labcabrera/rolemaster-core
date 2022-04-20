package org.labcabrera.rolemaster.core.model.tactical;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum TacticalCharacterState implements CodeNameEnum {

	NORMAL("normal", "Normal"),

	MASSIVE_SHOCK("massive-shock", "Massive Shock"),

	MASSIVE_SHOCK_DYING("massive-shock-dying", "Massive shock dying"),

	DEAD("dead", "Dead");

	private String code;

	private TacticalCharacterState(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Getter
	private String name;

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
