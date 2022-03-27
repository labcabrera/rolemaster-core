package org.labcabrera.rolemaster.core.model.skill;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SkillProgressionType {

	STANDARD("standard");

	private String name;

	SkillProgressionType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
