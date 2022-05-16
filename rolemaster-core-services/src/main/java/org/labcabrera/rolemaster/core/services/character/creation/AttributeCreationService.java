package org.labcabrera.rolemaster.core.services.character.creation;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.character.AttributeType;

public interface AttributeCreationService {

	Integer calculateCost(Map<AttributeType, Integer> values);

	Integer getCost(int value);

	Integer getPotentialStat(int value);

}