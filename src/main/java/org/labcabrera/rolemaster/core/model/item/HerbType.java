package org.labcabrera.rolemaster.core.model.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum HerbType implements CodeNameEnum {

	ANTIDOTE("antidote", "Antidote"),

	BONE_REPAIR("bone-repair", "Bone repair"),

	BURN_RELIEF("bone-repair", "Bone repair"),

	CIRCULATORY_REPAIR("circulatory-repair", "Circulatory repair"),

	CONCUSSION_RELIEF("concussion-relief", "Concussion relief"),

	GENERAL_PURPOSE("general-purpose", "General purpose"),

	LIFE_PRESERVATION("life-preservation", "Life preservation"),

	MUSCLE_REPAIR("muscle-repair", "Muscle repair"),

	NERVE_REPAIR("nerve-repair", "Nerve repair"),

	ORGAN_REPAIR("organ-repair", "Organ repair"),

	ENHANCEMENT("enhancement", "Enhancement"),

	STAT_MODIFIER("stat-modifier", "Stat modifier"),

	STUN_RELIEF("stun-relief", "Stun relief"),

	INTOXICANT("intoxicant", "Intoxicant");

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
