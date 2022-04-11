package org.labcabrera.rolemaster.core.model.npc;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum NpcSpeed {

	INCHING("IN", "Inching", -16),

	CREEPING("CR", "Creeping", -12),

	VERY_SLOW("VS", "Very Slow", -8),

	SLOW("SL", "Slow", -4),

	MEDIUM("MD", "Medium", 0),

	MODERATELY_FAST("MF", "Moderately Fast", 4),

	FAST("FA", "Fast", 8),

	VERY_FAST("FF", "Very Fast", 12),

	BLINDING_FAST("BF", "Blinding Fast", 16);

	private String name;

	@Getter
	private String description;

	@Getter
	private Integer initiativeModifier;

	private NpcSpeed(String name, String description, Integer initiativeModifier) {
		this.name = name;
		this.description = description;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
