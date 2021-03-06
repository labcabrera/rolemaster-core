package org.labcabrera.rolemaster.core.dto.character;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.spell.Realm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterCreation {

	@NotEmpty
	@Schema(description = "Character name.", example = "Kiove", required = true)
	private String name;

	@NotNull
	@Schema(description = "Rolemaster version.", example = "rmss", required = true)
	private RolemasterVersion version;

	@NotEmpty
	@Schema(description = "Universe identifier.", example = "middle-earth", required = true)
	private String universeId;

	@NotNull
	@Schema(description = "Character max level.", example = "1", required = true)
	private Integer level;

	@NotEmpty
	@Schema(description = "Characted race identifier.", example = "common-men", required = true)
	private String raceId;

	@NotEmpty
	@Schema(description = "Characted race identifier.", example = "figther", required = true)
	private String professionId;

	@NotNull
	@Schema(description = "Characted realm.", example = "essence", required = true)
	private Realm realm;

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

	@NotEmpty
	@Builder.Default
	@Schema(description = "Establishes the order of priority for the development of the weapons categories. The higher the priority, the lower the development cost.", required = true)
	private List<String> weaponCategoryPriority = new ArrayList<>();

	@Builder.Default
	private List<String> languages = new ArrayList<>();

}
