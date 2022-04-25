package org.labcabrera.rolemaster.core.model.character.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum ItemFeatureType implements CodeNameEnum {

	BROKEN("broken", "Broken", false),

	BONUS("bonus", "Bonus", false),

	MATERIAL("material", "Material", true),

	SLAYER("slayer", "Slayer", true),

	FLUMBE("flumbe", "Flumbe", false),

	SHIELD_BONUS("shield-bonus", "Shield bonus", false),

	UNBREAKABLE("unbreakable", "Unbreakable", false),

	BREAKAGE("breakage", "Breakage", false),

	/** Weapon strength for breakage check. */
	BREAKAGE_STRENGTH("breakage-strength", "Breakage strength", false),

	ADDITIONAL_CRITICAL("additional-critical", "Additional critical", true),

	SHATTER_PARRY_WEAPON("shatter-parry-weapon", "Shatter parry weapon", false),

	SPELL_ON_HIT("spell-on-hit", "Spell on hit", true),

	DIRECT_SPELL_ON_HIT("direct-spell-on-hit", "Direct spell on hit", true);

	private String code;

	@Getter
	private String name;

	@Getter
	private boolean allowMultipleValues;

	private ItemFeatureType(String code, String name, boolean allowMultipleValues) {
		this.code = code;
		this.name = name;
		this.allowMultipleValues = allowMultipleValues;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
