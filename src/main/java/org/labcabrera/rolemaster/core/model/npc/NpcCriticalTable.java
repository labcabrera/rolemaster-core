package org.labcabrera.rolemaster.core.model.npc;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum NpcCriticalTable implements CodeNameEnum {

	DEFAULT("default", "Default"),

	LARGE("large", "Large"),

	SUPER_LARGE("super-large", "Super Large");

	private String code;

	@Getter
	private String name;

	private NpcCriticalTable(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
