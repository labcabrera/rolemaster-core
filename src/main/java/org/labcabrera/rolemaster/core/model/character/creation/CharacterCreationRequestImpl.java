package org.labcabrera.rolemaster.core.model.character.creation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	@Schema(description = "Character name.", example = "Kiove", required = true)
	private String name;

	@ExistingRace
	@NotEmpty
	@Schema(description = "Characted race identifier.", example = "common-men", required = true)
	private String raceId;

	@NotEmpty
	@Schema(description = "Characted race identifier.", example = "figther", required = true)
	private String professionId;

	@NotEmpty
	@Schema(description = "Characted realm identifier.", example = "essence", required = true)
	private String realmId;

	@NotNull
	@Schema(description = "Character age in years", example = "32", required = true)
	private Integer age;

	@NotNull
	@Schema(description = "Character height in cm.", example = "184", required = true)
	private Integer height;

	@NotNull
	@Schema(description = "Character height in kg.", example = "85", required = true)
	private Integer weight;

	@NotNull
	private Integer attributesRoll;

	@NotEmpty
	@Builder.Default
	private Map<AttributeType, Integer> baseAttributes = new LinkedHashMap<>();

	@Builder.Default
	private List<String> weaponCategoryOrder = new ArrayList<>();

	@Builder.Default
	private List<String> languages = new ArrayList<>();

}
