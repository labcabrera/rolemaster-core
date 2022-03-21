package org.labcabrera.rolemaster.core.model.character.creation.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.validation.ExistingRaceValidator.ExistingRace;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterCreationRequestImpl implements CharacterCreationRequest {

	@Schema(description = "Character name", example = "Kiove")
	@NotEmpty
	private String name;

	@Schema(description = "Characted race identifier", example = "human")
	@ExistingRace
	@NotEmpty
	private String raceId;

	private Integer attributesRoll;

	@Builder.Default
	private Map<AttributeType, Integer> baseAttributes = new LinkedHashMap<>();

}
