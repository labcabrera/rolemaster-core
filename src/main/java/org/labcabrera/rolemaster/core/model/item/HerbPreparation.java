package org.labcabrera.rolemaster.core.model.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum HerbPreparation implements CodeNameEnum {

	PASTE("paste", "Paste"),

	POWDER("powder", "Powder"),

	INGEST("ingest", "Ingest"),

	INJECT("inject", "Inject"),

	LIQUID("liquid", "Liquid"),

	BREW("brew", "Brew"),
	
	APPLY("apply", "Apply");

	private String code;

	private String name;

	private HerbPreparation(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
