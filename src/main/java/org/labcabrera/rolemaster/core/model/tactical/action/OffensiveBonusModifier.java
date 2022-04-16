package org.labcabrera.rolemaster.core.model.tactical.action;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OffensiveBonusModifier {

	SKILL("skill"),

	DEFENSIVE_BONUS("defensive-bonus"),

	SHIELD("shield"),

	TARGET_STATUS("target-status"),

	HP("hp"),

	EXHAUSTION("exhaustion"),

	ACTION_PERCENT("action-percent"),

	MELEE_FACING("melee-facing"),

	PARRY("parry"),

	DISTANCE("missile-distance");

	private String name;

	private OffensiveBonusModifier(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
