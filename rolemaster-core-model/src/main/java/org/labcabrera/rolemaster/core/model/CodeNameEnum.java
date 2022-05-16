package org.labcabrera.rolemaster.core.model;

import com.fasterxml.jackson.annotation.JsonValue;

public interface CodeNameEnum {

	@JsonValue
	String getCode();

	String getName();

}
