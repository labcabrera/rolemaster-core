package org.labcabrera.rolemaster.core.model.character.creation;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.character.AttributeType;

public interface CharacterCreationRequest {

	String getName();

	String getRaceId();

	String getProfessionId();

	String getRealmId();

	Integer getAttributesRoll();

	Map<AttributeType, Integer> getBaseAttributes();

}
