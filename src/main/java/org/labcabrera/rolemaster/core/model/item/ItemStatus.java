package org.labcabrera.rolemaster.core.model.item;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemStatus {

	/** Equipment currently carried by the character (which affects its weight). */
	EQUIPED("equiped"),

	/** Items possessed by character but not carrying it. */
	ONWED("owned"),

	/**
	 * Equipment owned by the character but not directly accessible from the adventure
	 * (e.g. stored at home).
	 */
	STORED("stored");

	private String name;

	private ItemStatus(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
