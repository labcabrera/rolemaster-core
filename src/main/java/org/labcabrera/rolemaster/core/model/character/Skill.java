package org.labcabrera.rolemaster.core.model.character;

import java.util.ArrayList;
import java.util.List;

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

	private String categoryId;

	@Builder.Default
	private Boolean loadOnNewCharacters = false;

	@Builder.Default
	private List<AttributeType> attributeBonus = new ArrayList<>();

	private SkillProgressionType progressionType;

	private String categoryBonusProgression;

	@Builder.Default
	private Integer skillBonus = 0;

}
