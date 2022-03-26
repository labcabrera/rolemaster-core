package org.labcabrera.rolemaster.core.model.character;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "professions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of = "id")
public class Profession {

	@Schema(description = "Profession identifier")
	@Id
	private String id;

	@Schema(description = "Profession name")
	private String name;

	@Schema(description = "Available realms")
	private List<String> availableRealms;

	@Builder.Default
	private Map<String, Integer> skillCategoryBonus = new LinkedHashMap<>();

	@Builder.Default
	private Map<String, List<Integer>> skillCategoryDevelopmentCost = new LinkedHashMap<>();

	@Builder.Default
	private List<List<Integer>> skillCategoryWeaponDevelopmentCost = new ArrayList<>();

}
