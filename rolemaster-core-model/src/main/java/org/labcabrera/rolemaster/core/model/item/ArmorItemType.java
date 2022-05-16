package org.labcabrera.rolemaster.core.model.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum ArmorItemType implements CodeNameEnum {

	ARMOR("armor", "Armor"),

	ACCESSORY("accessory", "Accessory"),

	SHIELD("shield", "Shield"),

	HELMET("helmet", "Helmet");

	private String code;

	@Getter
	private String name;

	private ArmorItemType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
