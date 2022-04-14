package org.labcabrera.rolemaster.core.model.combat;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConditionalCriticalEffect {

	WITH_LEG_GREAVE("with-leg-greave"),

	WITHOUT_LET_GREAVE("without-leg-greave"),

	WITH_HELMET("with-helmet"),

	WITHOUT_HELMET("without-helmet"),

	WITH_WAIST_ARMOR("with-waist-armor"),

	WITHOUT_WAIST_ARMOR("without-waist-armor");

	private String name;

	private ConditionalCriticalEffect(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
