package org.labcabrera.rolemaster.core.model.tactical;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum Buff implements CodeNameEnum {

	HAS_INITIATIVE("has-initiative", "Has initiative");

	private String code;

	private Buff(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Getter
	private String name;

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
