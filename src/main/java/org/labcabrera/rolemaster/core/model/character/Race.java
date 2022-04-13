package org.labcabrera.rolemaster.core.model.character;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("races")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Race {

	@Id
	private String id;

	private String name;

	private EntityMetadata metadata;

	@Builder.Default
	private Map<AttributeType, Integer> attributeModifiers = new LinkedHashMap<>();

	@Builder.Default
	private Map<String, Integer> adolescenseSkillCategoryRanks = new LinkedHashMap<>();

	@Builder.Default
	private Map<String, Integer> adolescenseSkillRanks = new LinkedHashMap<>();

	@Builder.Default
	private List<Integer> bodyDevelopmentProgression = new ArrayList<>();

	@Builder.Default
	private Map<String, List<Integer>> ppDevelopmentProgression = new HashMap<>();

	@Builder.Default
	private List<String> universes = new ArrayList<>();

	@Builder.Default
	private Map<ResistanceType, Integer> resistanceBonus = new EnumMap<>(ResistanceType.class);

}
