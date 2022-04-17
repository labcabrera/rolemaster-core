package org.labcabrera.rolemaster.core.model.character;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum AttributeType implements CodeNameEnum {

	AGILITY("ag", "Agility"),

	CONSTITUTION("co", "Constitution"),

	MEMORY("me", "Memory"),

	REASONING("re", "Reasoning"),

	SELF_DISCIPLINE("sd", "Self discipline"),

	EMPATHY("em", "Empaty"),

	INTUTITION("in", "Intuition"),

	PRESENCE("pr", "Presence"),

	QUICKNESS("qu", "Quickness"),

	STRENGTH("st", "Strength");

	private String code;

	@Getter
	private String name;

	AttributeType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
