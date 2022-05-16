package org.labcabrera.rolemaster.core.security;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {

	ADMIN("admin");

	private String code;

	private Role(String code) {
		this.code = code;
	}

	@JsonValue
	public String getCode() {
		return code;
	}
}
