package org.labcabrera.rolemaster.core.model.character;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.spell.Realm;
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
@Schema(description = "Defines the generic information associated with a character that only changes with training or learning. Additionally there are other entities (e.g. the character's inventory). Combat specific information is defined at the tactical session level.")
public class CharacterInfo {

	@Id
	@Schema(description = "Character identifier.", required = true, example = "6242c18da7a9f7048331ca03")
	private String id;

	@Schema(description = "Character name.", required = true, example = "Bilbo Baggins")
	private String name;

	@Schema(description = "Character realm.", required = true, example = "channeling")
	private Realm realm;

	@Schema(description = "Character current level. Zero if character is under creation.", required = true, example = "3")
	private Integer level;

	@Schema(description = "The maximum level up to which the character can be raised from experience or generation options.", required = true, example = "7")
	private Integer maxLevel;

	@Schema(description = "Character age in years.", required = true, example = "42")
	private Integer age;

	@Schema(description = "Character height in cm.", required = true, example = "184")
	private Integer height;

	@Schema(description = "Character race identifier.", required = true, example = "common-men")
	private String raceId;

	@Schema(description = "Character profession identifier.", required = true, example = "thief")
	private String professionId;

	@Schema(description = "Character creation status.", required = true, example = "created")
	private CharacterStatus status;

	@Builder.Default
	@Schema(description = "Character attributes.", required = true)
	private Map<AttributeType, CharacterAttribute> attributes = new LinkedHashMap<>();

	@Builder.Default
	@Schema(description = "Character skill categories.", required = true)
	private List<CharacterSkillCategory> skillCategories = new ArrayList<>();

	@Builder.Default
	@Schema(description = "Character skills. This list is dynamic and new values can be added.", required = true)
	private List<CharacterSkill> skills = new ArrayList<>();

	@Builder.Default
	@Schema(description = "Character resistances.", required = true)
	private Map<ResistanceType, CharacterResistance> resistances = new LinkedHashMap<>();

	@Builder.Default
	@Schema(description = "Training packages adquired.")
	private Map<String, LifestyleTrainingType> trainingPackages = new LinkedHashMap<>();

	@Builder.Default
	@Schema(description = "List of character development points. These points are used for both creation and leveling up.", required = true)
	private CharacterDevelopment developmentPoints = new CharacterDevelopment();

	@Schema(description = "Character max hit points. It is calculated from the Body Development ability.", required = true, example = "78")
	private Integer maxHp;

	@Schema(description = "Character max power points.", required = true, example = "42")
	private Integer maxPowerPoints;

	@Schema(description = "Maximum number of fatigue points of the character.", required = true, example = "66")
	private BigDecimal maxExhaustionPoints;

	@Schema(description = "Experience ponints", required = true, example = "7632")
	private Long xp;

	@Schema(description = "Value in meters.", required = true, example = "15")
	private Integer baseMovementRate;

	@Builder.Default
	private CharacterArmor armor = new CharacterArmor();

	@Builder.Default
	private Integer defensiveBonus = 0;

	@Builder.Default
	private CharacterWeight weight = new CharacterWeight();

	@Builder.Default
	private List<String> notes = new ArrayList<>();

	@Builder.Default
	@Schema(description = "Audit data.", required = true)
	private EntityMetadata metadata = EntityMetadata.builder().created(LocalDateTime.now()).build();

	@Builder.Default
	@Schema(description = "Authorization restrictions")
	private List<String> authorization = new ArrayList<>();

	public Optional<CharacterSkillCategory> getSkillCategory(String categoryId) {
		return skillCategories.stream().filter(e -> categoryId.equals(e.getCategoryId())).findFirst();
	}

	public Optional<CharacterSkill> getSkill(String skillId) {
		return skills.stream().filter(e -> skillId.equals(e.getSkillId())).findFirst();
	}

}
