package org.labcabrera.rolemaster.core.model.character;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

@Document(collection = "characters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterInfo {

	@Id
	private String id;

	private String name;

	private String raceId;

	private CharacterCreationStatus creationStatus;

	@Builder.Default
	private List<String> professions = new ArrayList<>();

	@Builder.Default
	private Map<String, Resistance> resistances = new LinkedHashMap<>();

	@Builder.Default
	private Map<AttributeType, CharacterAttribute> attributes = new LinkedHashMap<AttributeType, CharacterAttribute>();

	@Builder.Default
	private List<CharacterSkillCategory> skillCategories = new ArrayList<>();

	@Builder.Default
	private List<CharacterSkill> skills = new ArrayList<>();

	private Integer maxHp;

	private Integer maxExhaustionPoints;

	private Integer xp;

	@Builder.Default
	private EntityMetadata metadata = EntityMetadata.builder().created(LocalDateTime.now()).build();

	@Builder.Default
	private List<String> authorization = new ArrayList<>();

}
