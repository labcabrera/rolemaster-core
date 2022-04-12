package org.labcabrera.rolemaster.core.model.tactical.action;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OffensiveBonusModifier {

	SKILL("skill"),

	DEFENSIVE_BONUS("defensiveBonus"),

	SHIELD_BONUS("shieldBonus"),

	TARGET_STATUS("targetStatus"),

	HP("hp"),

	EXHAUSTION("exhaustion"),

	ACTION_PERCENT("actionPercent"),

	MELEE_POSITION("meleePosition"),

	MELEE_PARRY("meleeParry"),

	MISSILE_DISTANCE("missileDistance");

	private String name;

	private OffensiveBonusModifier(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
