package org.labcabrera.rolemaster.core.model.item;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CoinType {

	GOLD("g"),

	SILVER("s"),

	COPPER("c");

	private String name;

	private CoinType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
