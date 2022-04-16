package org.labcabrera.rolemaster.core.model.tactical;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum InjuryType implements CodeNameEnum {

	ARM_USELESS("arm-useless", "Arm useless"),

	ARMS_USELESS("arms-useless", "Arms useless"),

	HAND_BROKEN("hand-broken", "Hand broken"),

	EYES_DESTROYED("eyes-destroyed", "Eyes destroyed"),

	BACK_BONES_BROKEN("back-bones-broken", "Back bones broken"),

	BACK_MUSCLES_AND_CARTILAGE_DAMAGED("back-muscle-and-cartilage-damaged", "Back muscles and cartilage are damaged"),

	BREAK_NOSE("break-nose", "Break nose"),

	PARALYZED_NECK_DOWN("paralyzed-neck-down", "Paralized neck down"),

	COMA("coma", "Coma")

	;

	private String code;

	@Getter
	private String name;

	private InjuryType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
