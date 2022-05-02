package org.labcabrera.rolemaster.core.model.combat;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum Cover implements CodeNameEnum {

	NONE("none", "None", 0, 0),

	PARTIAL_SOFT("partial-soft", "Partial Soft", 10, 20),

	PARTIAL_HARD("partial-hard", "Partial Hard", 15, 30),

	HALF_SOFT("half-soft", "Half Soft", 20, 40),

	HALF_HARD("full-hard", "Half Hard", 30, 60),

	FULL_SOFT("full-soft", "Full Soft", 0, 0),

	FULL_HARD("full-hard", "Full Hard", 0, 0);

	private String code;

	private String name;

	private Integer meleeBonus;

	private Integer missileBonus;

	private Cover(String code, String name, Integer meleeBonus, Integer missileBonus) {
		this.code = code;
		this.name = name;
		this.meleeBonus = meleeBonus;
		this.missileBonus = missileBonus;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
