package org.labcabrera.rolemaster.core.model.character;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
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

	private Integer level;

	private Integer age;

	private Integer height;

	private Integer weight;

	private String name;

	private String raceId;

	private CharacterCreationStatus creationStatus;

	private String professionId;

	@Builder.Default
	private Map<String, Resistance> resistances = new LinkedHashMap<>();

	@Builder.Default
	private Map<AttributeType, CharacterAttribute> attributes = new LinkedHashMap<>();

	@Builder.Default
	private List<CharacterSkillCategory> skillCategories = new ArrayList<>();

	@Builder.Default
	private List<CharacterSkill> skills = new ArrayList<>();

	@Builder.Default
	private CharacterDevelopment developmentPoints = new CharacterDevelopment();

	private Integer maxHp;

	private Integer maxExhaustionPoints;

	private Integer xp;

	@Schema(description = "Value in meters.")
	private Integer baseMovementRate;

	@Builder.Default
	private EntityMetadata metadata = EntityMetadata.builder().created(LocalDateTime.now()).build();

	@Builder.Default
	private List<String> authorization = new ArrayList<>();

	public Optional<CharacterSkillCategory> getSkillCategory(String categoryId) {
		return skillCategories.stream().filter(e -> categoryId.equals(e.getCategoryId())).findFirst();
	}

	public Optional<CharacterSkill> getSkill(String skillId) {
		return skills.stream().filter(e -> skillId.equals(e.getSkillId())).findFirst();
	}

}
