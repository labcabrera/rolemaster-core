package org.labcabrera.rolemaster.core.services.character.creation;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributeModifiers;
import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributes;
import org.labcabrera.rolemaster.core.model.RolemasterVersionService;
import org.labcabrera.rolemaster.core.model.character.AttributeType;

import reactor.core.publisher.Mono;

public interface AttributeCreationService extends RolemasterVersionService {

	@Deprecated
	Integer calculateCost(Map<AttributeType, Integer> values);

	@Deprecated
	Integer getCost(int value);

	Integer getPotentialStat(int value);

	Mono<CharacterCreationAttributeModifiers> getAttributeModifiers(@NotNull @Valid CharacterCreationAttributes attributes);

}