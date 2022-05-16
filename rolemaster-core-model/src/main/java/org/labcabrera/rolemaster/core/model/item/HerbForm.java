package org.labcabrera.rolemaster.core.model.item;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum HerbForm implements CodeNameEnum {

	LEAF("leaf", "Leaf"),

	ROOT("root", "Root"),

	BARK("bark", "Bark"),

	LIQUID("liquid", "Liquid"),

	PASTE("paste", "Paste"),

	FLOWER("flower", "Flower"),

	ASP("asp", "Asp"),

	SCORPION("scorpion", "Scorpion"),

	STALK("stalk", "Stalk"),

	SPIDER("spider", "Spider"),

	BERRY("berry", "Berry"),

	GAS("gas", "Gas"),

	BATS("bats", "Bats"),

	CLAMS("clams", "Clams");

	private String code;

	private String name;

	private HerbForm(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@JsonValue
	@Override
	public String getCode() {
		return code;
	}

}
