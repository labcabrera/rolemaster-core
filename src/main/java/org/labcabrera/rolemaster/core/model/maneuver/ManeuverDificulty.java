package org.labcabrera.rolemaster.core.model.maneuver;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum ManeuverDificulty implements CodeNameEnum {

	NONE("none", "NONE"),

	ROUTINE("routine", "Routine"),

	EASY("easy", "Easy"),

	LIGHT("light", "Light"),

	MEDIUM("medium", "Medium"),

	HARD("hard", "Hard"),

	VERY_HARD("very-hard", "Very Hard"),

	EXTREMELY_HARD("extremely-hard", "Extremely Hard"),

	SHEER_FOLLY("sheer-folly", "Sheer-folly"),

	ABSURD("absurd", "Absurd");

	private String code;

	@Getter
	private String name;

	private ManeuverDificulty(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
