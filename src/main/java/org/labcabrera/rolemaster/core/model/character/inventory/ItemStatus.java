package org.labcabrera.rolemaster.core.model.character.inventory;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum ItemStatus {

	/** Equipment currently carried by the character (which affects its weight). */
	CARRIED("carried", "Carried"),

	MAIN_HAND("main-hand", "Main hand"),

	OFF_HAND("off-hand", "Off-hand"),

	ARMOR("armor", "Armor"),

	ARMOR_ACCESSORY("armor-accessory", "Armor accesory"),

	/**
	 * Equipment owned by the character but not directly accessible from the adventure
	 * (e.g. stored at home).
	 */
	STORED("stored", "Stored");

	private String code;

	@Getter
	private String name;

	private ItemStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
