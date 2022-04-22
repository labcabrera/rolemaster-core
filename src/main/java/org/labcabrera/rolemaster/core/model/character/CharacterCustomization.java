package org.labcabrera.rolemaster.core.model.character;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Talents and flaws.")
@Document(collection = "characterCustomization")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterCustomization {

	@Id
	private String id;

	private String name;

	private CharacterCustomizationType type;

	private CharacterCustomizationCategory category;

	private CharacterCustomizationCostType costType;

	private Integer cost;

	private Map<String, Integer> categoryBonus;

	private Map<String, Integer> skillBonus;

	private String description;

}
