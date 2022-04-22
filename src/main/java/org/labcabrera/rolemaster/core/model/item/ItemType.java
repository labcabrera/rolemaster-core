package org.labcabrera.rolemaster.core.model.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum ItemType implements CodeNameEnum {

	WEAPON("weapon", "Weapon"),

	ARMOR_PIECE("armor-piece", "Armor Piece"),

	MISCELLANEOUS("miscellaneous", "Miscellaneous");

	private String code;

	@Getter
	private String name;

	private ItemType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
