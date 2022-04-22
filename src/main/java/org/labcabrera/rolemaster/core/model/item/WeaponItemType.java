package org.labcabrera.rolemaster.core.model.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum WeaponItemType implements CodeNameEnum {

	ONE_HAND("h1", "1H"),

	TWO_HANDS("h2", "2H"),

	ONE_OR_TWO_HANDS("h1-h2", "1H/2H"),

	MISSILE("missile", "Missile");

	private String code;

	@Getter
	private String name;

	private WeaponItemType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
