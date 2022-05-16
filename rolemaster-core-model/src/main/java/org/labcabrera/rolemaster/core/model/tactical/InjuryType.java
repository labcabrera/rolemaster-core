package org.labcabrera.rolemaster.core.model.tactical;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum InjuryType implements CodeNameEnum {

	RIGHT_ARM_USELESS("right-arm-useless", "Right arm useless"),

	LEFT_ARM_USELESS("left-arm-useless", "Left arm useless"),

	RIGHT_HAND_USELESS("right-hand-useless", "Right hand useless"),

	LEFT_HAND_USELESS("left-hand-useless", "Left hand useless"),

	WRIST_BROKEN("wrist-broken", "Wrist broken"),

	NECK_BROKEN("neck-broken", "Neck broken"),

	LEG_USELESS("leg-useless", "Leg useless"),

	SPRAIN_ANKLE("sprain-ankle", "Sprain ankle"),

	KNEE_TEAR_LIGAMENT("knee-tear-ligament", "Knee tear ligament"),

	SPINE_BROKEN("spine-broken", "Spine broken"),

	EYES_DESTROYED("eyes-destroyed", "Eyes destroyed"),

	BACK_BONES_BROKEN("back-bones-broken", "Back bones broken"),

	BACK_MUSCLES_AND_CARTILAGE_DAMAGED("back-muscle-and-cartilage-damaged", "Back muscles and cartilage are damaged"),

	BREAK_NOSE("break-nose", "Break nose"),

	PARALYZED_NECK_DOWN("paralyzed-neck-down", "Paralized neck down"),

	COMA("coma", "Coma"),

	SHOULDER_BROKEN("shoulder-broken", "Shoulder broken");

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
