package org.labcabrera.rolemaster.core.model.character;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RankType {

	ADOLESCENCE("adolescence"),

	TRAINING_PACKAGE("training-package"),

	CONSOLIDATED("consolidated"),

	DEVELOPMENT("development");

	private String name;

	private RankType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
