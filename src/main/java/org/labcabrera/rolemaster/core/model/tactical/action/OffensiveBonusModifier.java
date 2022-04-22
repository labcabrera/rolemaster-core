package org.labcabrera.rolemaster.core.model.tactical.action;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OffensiveBonusModifier {

	SKILL("skill"),

	DEFENSIVE_BONUS("defensive-bonus"),

	SHIELD("shield"),

	TARGET_STATUS("target-status"),

	HP("hp"),

	EXHAUSTION("exhaustion"),

	PENALTY("penalty"),

	BONUS("bonus"),

	ACTION_PERCENT("action-percent"),

	MELEE_FACING("melee-facing"),

	PARRY_ATTACK("parry-attack"),

	PARRY_DEFENSE("parry-defense"),

	DISTANCE("missile-distance"),

	OFF_HAND("off-hand");

	private String name;

	private OffensiveBonusModifier(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
