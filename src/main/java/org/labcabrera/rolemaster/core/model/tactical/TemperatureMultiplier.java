package org.labcabrera.rolemaster.core.model.tactical;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum TemperatureMultiplier implements CodeNameEnum {

	NORMAL("normal", "Normal", 1),

	ABOVE_37("above-37", "Above 37ºC (x2)", 2),

	ABOVE_49("above-49", "Above 49ºC (x4)", 4),

	ABOVE_54("above-54", "Above 54ºC (x8)", 8),

	BELOW_M7("above-m7", "Below -7ºC (x2)", 2),

	BELOW_M23("above-m23", "Below -23ºC (x3)", 3),

	BELOW_M34("above-m34", "Below -34ºC (x5)", 5),

	BELOW_M45("above-m45", "Below -45ºC (x8)", 8);

	private String code;

	@Getter
	private String name;

	@Getter
	private Integer multiplier;

	private TemperatureMultiplier(String code, String name, Integer multiplier) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
