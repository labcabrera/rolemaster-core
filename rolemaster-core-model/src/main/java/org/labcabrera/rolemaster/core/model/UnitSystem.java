package org.labcabrera.rolemaster.core.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum UnitSystem implements CodeNameEnum {

	METRIC("metric", "Metric"),

	IMPERIAL("imperial", "Imperial");

	private String code;

	private String name;

	private UnitSystem(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
