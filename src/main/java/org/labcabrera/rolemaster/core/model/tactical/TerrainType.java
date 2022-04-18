package org.labcabrera.rolemaster.core.model.tactical;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum TerrainType {

	NORMAL("normal", "Normal", 1),

	ROUGH("rough", "Rough (x2)", 2),

	MONTAINOUS("mountainous", "Mountainous (x3)", 3),

	SAND("sand", "Sand (x3)", 3),

	BOG("bog", "Bog (x4)", 4);

	private String code;

	@Getter
	private String name;

	@Getter
	private Integer multiplier;

	private TerrainType(String code, String name, Integer multiplier) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
