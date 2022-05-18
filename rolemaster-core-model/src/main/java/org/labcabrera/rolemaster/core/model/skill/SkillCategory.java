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

@Document(collection = "skillCategories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillCategory {

	@Id
	private String id;

	private String name;

	private RolemasterVersion version;

	private String description;

	private String group;

	@Builder.Default
	private List<AttributeType> attributeBonus = new ArrayList<>();

	@Builder.Default
	Boolean useRealmAttributeBonus = false;

	@Builder.Default
	private List<SkillModifier> modifiers = new ArrayList<>();

	@Builder.Default
	private SkillProgressionType progressionType = SkillProgressionType.CATEGORY;

}
