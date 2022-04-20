package org.labcabrera.rolemaster.core.model.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum ItemFeature implements CodeNameEnum {

	MITHRIL("mithril", "Mithril"),

	ITHILNAUR("ithilnaur", "Ithilnaur"),

	UNBREAKABLE("unbreakable", "Unbreakable"),

	ELF_SLAYER("elf-slayer", "Elf slayer"),
	
	MAN_SLAYER("man-slayer", "Man slayer"),

	ORC_SLAYER("ork-slayer", "Orc slayer"),

	/** If attack result is 0 HP. */
	WEAPON_SHATTER_LVL_60("weapon-shatter-lvl-60", "Weapon Shatter (60)"),

	EXTRA_CRITICAL_HEAT("extra-critical-heat", "Extra Critical Heat");

	private String code;

	@Getter
	private String name;

	private ItemFeature(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
