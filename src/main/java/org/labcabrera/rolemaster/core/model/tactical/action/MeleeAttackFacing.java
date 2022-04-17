package org.labcabrera.rolemaster.core.model.tactical.action;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import lombok.Getter;

@Getter
public enum MeleeAttackFacing implements CodeNameEnum {

	NORMAL("normal", "Normal", 0),

	FLANK("flank", "Flank (+15)", 15),

	REAR_FLANK("rear-flank", "Read Flank (+25)", 25),

	REAR("rear", "Rear (+35)", 35);

	private String code;

	private Integer modifier;

	private String name;

	private MeleeAttackFacing(String code, String name, Integer modifier) {
		this.code = code;
		this.name = name;
		this.modifier = modifier;
	}

}
