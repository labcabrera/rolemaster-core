package org.labcabrera.rolemaster.core.model.character;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResistanceType {

	CHANNELING("channeling"),

	ESSENCE("essence"),

	MENTALISM("mentalism"),

	POISON("poison"),

	DISEASE("disease"),

	FEAR("fear");

	private String name;

	private ResistanceType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
