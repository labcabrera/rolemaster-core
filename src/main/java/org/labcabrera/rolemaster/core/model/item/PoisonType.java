package org.labcabrera.rolemaster.core.model.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum PoisonType implements CodeNameEnum {

	CIRCULATORY("circulatory", "Circulatory"),

	CONVERSION("conversion", "Conversion"),

	MUSCLE("muscle", "Muscle"),

	NERVE("nerve", "Nerve"),

	REDUCTION("reduction", "Reduction"),

	RESPIRATORY("respiratory", "Respiratory");

	private PoisonType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private String code;

	private String name;

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
