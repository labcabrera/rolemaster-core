package org.labcabrera.rolemaster.core.model.character.creation;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
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

	@NotEmpty
	@Schema(description = "Character name.", example = "Kiove")
	private String name;

	@ExistingRace
	@NotEmpty
	@Schema(description = "Characted race identifier.", example = "common-men")
	private String raceId;

	@NotEmpty
	@Schema(description = "Characted race identifier.", example = "figther")
	private String professionId;

	private Integer attributesRoll;

	@NotEmpty
	@Builder.Default
	private Map<AttributeType, Integer> baseAttributes = new LinkedHashMap<>();

}
