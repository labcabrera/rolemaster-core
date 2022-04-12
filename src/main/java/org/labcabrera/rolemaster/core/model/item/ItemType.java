package org.labcabrera.rolemaster.core.model.item;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemType {

	MISCELLANEOUS("miscellaneous"),

	WEAPON("weapon"),

	SHIELD("shield"),

	ARMOR("armor"),

	ARMOR_ACCESSORY("armorAccessory");

	private String name;

	private ItemType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
