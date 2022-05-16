package org.labcabrera.rolemaster.core.model.combat;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import lombok.Getter;

public enum LargeCreatureCriticalType implements CodeNameEnum {

	NORMAL("normal", "Normal"),

	MAGIC("magic", "Magic"),

	MITHRIL("mithril", "Mithril"),

	HOLY("holy", "Holy"),

	SLAYING("slaying", "Slaying");

	private LargeCreatureCriticalType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private String code;

	@Getter
	private String name;

	@Override
	public String getCode() {
		return code;
	}

}
