package org.labcabrera.rolemaster.core.model.character.inventory;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemStatus {

	/** Equipment currently carried by the character (which affects its weight). */
	CARRIED("carried"),

	MAIN_HAND("mainHand"),

	OFF_HAND("offHand"),

	ARMOR("armor"),

	ARMOR_ACCESSORY("armorAccessory"),

	/**
	 * Equipment owned by the character but not directly accessible from the adventure
	 * (e.g. stored at home).
	 */
	STORED("stored");

	private String name;

	private ItemStatus(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
