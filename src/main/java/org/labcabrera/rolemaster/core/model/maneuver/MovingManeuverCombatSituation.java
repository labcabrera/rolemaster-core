package org.labcabrera.rolemaster.core.model.maneuver;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum MovingManeuverCombatSituation implements CodeNameEnum {

	NONE("none", "None", 0),

	ENGADED_IN_MELEE("engaged-in-melee", "Engaged in melee", -30),

	MELEE_ENVIRONMENT("melee-environment", "Melee environment", -20),

	UNDER_MISSILE_FIRE("under-missile-fire", "Under missile fire", -10);

	private String code;

	private String name;

	private int bonus;

	private MovingManeuverCombatSituation(String code, String name, int bonus) {
		this.code = code;
		this.name = name;
		this.bonus = bonus;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
