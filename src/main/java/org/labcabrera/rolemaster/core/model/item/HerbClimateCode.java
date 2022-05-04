package org.labcabrera.rolemaster.core.model.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum HerbClimateCode implements CodeNameEnum {

	ARID("arid", "Arid"),

	COLD("cold", "Cold"),

	EVERLASTING_COLD("everlasting-cold", "Everlasting cold"),

	FRIGID("frigid", "Frigid (severe cold)"),

	HOT_AND_HUMID("hot-and-humid", "Hot and humid"),

	MILD("mild", "Mild temperature"),

	SEMI_ARID("semi-arid", "Semi arid"),

	COOL("cool", "Cool");

	private String code;

	private String name;

	private HerbClimateCode(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
