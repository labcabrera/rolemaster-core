package org.labcabrera.rolemaster.core.model.combat;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConditionalCriticalEffect {

	WITH_LEG_GREAVE("with-leg-greave"),

	WITHOUT_LET_GREAVE("without-leg-greave"),

	WITH_ARM_GREAVE("with-arm-greave"),

	WITHOUT_ARM_GREAVE("without-arm-greave"),

	WITH_HELMET("with-helmet"),

	WITHOUT_HELMET("without-helmet"),

	WITH_WAIST_ARMOR("with-waist-armor"),

	WITHOUT_WAIST_ARMOR("without-waist-armor"),

	WITH_LEG_ARMOR("with-leg-armor"),

	WITHOUT_LEG_ARMOR("without-leg-armor"),

	WITH_PLATE_CHEST("with-plate-chest"),

	WITHOUT_PLATE_CHEST("without-plate-chest"),

	WITH_ABDOMEN_ARMOR("with-abdomen-armor"),

	WITHOUT_ABDOMEN_ARMOR("without-abdomen-armor"),

	WITH_SHIELD("with-shield"),

	WITHOUT_SHIELD("without-shield"),

	WITH_NOSE_GUARD("with-nose-guard"),

	WITHOUT_NOSE_GUARD("without-nose-guard"),

	WITH_SHOULDER_ARMOR("with-shoulder-armor"),

	WITHOUT_SHOULDER_ARMOR("without-shoulder-armor"),

	WITH_THROAT_PROTECTOR("with-throat-protector"),

	WITHOUT_THROAT_PROTECTOR("without-throat-protector");

	private String name;

	private ConditionalCriticalEffect(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
