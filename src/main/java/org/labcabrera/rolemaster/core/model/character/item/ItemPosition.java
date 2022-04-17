package org.labcabrera.rolemaster.core.model.character.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public enum ItemPosition implements CodeNameEnum {

	@Schema(description = "Only weapon.")
	MAIN_HAND("main-hand", "Main hand"),

	@Schema(description = "Weapon or shield.")
	OFF_HAND("off-hand", "Off-hand"),

	@Schema(description = "Only armon pieces except shields.")
	EQUIPED("equipped", "Equipped"),

	@Schema(description = "Equipment currently carried by the character (which affects its weight). Any item.")
	CARRIED("carried", "Carried"),

	@Schema(description = "Equipment owned by the character but not directly accessible from the adventure (e.g. stored at home).")
	STORED("stored", "Stored");

	private String code;

	@Getter
	private String name;

	private ItemPosition(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
