package org.labcabrera.rolemaster.core.model.skill;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "skills")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of = "id")
public class Skill {

	@Id
	private String id;

	private String name;

	private RolemasterVersion version;

	private String categoryId;

	private SkillType type;

	@Builder.Default
	private Integer customizableOptions = 0;

	private SkillCustomizationRestriction customizationRestriction;

	@Builder.Default
	private Boolean loadOnNewCharacters = false;

	@Builder.Default
	private List<AttributeType> attributeBonus = new ArrayList<>();

	@Builder.Default
	private SkillProgressionType progressionType = SkillProgressionType.STANDARD;

	@Builder.Default
	private Integer skillBonus = 0;

	private String description;

	@Builder.Default
	private List<SkillModifier> modifiers = new ArrayList<>();

}
