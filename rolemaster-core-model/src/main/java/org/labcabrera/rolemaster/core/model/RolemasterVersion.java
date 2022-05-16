package org.labcabrera.rolemaster.core.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum RolemasterVersion implements CodeNameEnum {

	RMSS("rmss", "Rolemaster Standard System"),

	RMU("rmu", "Rolemaster Unified");

	private String code;

	private String name;

	private RolemasterVersion(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
