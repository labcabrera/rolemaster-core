package org.labcabrera.rolemaster.core.model.character;

import java.util.List;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "skills")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Skill {

	@Id
	private String id;

	private String name;

	private String description;

	private String categoryId;

	@Builder.Default
	private Boolean loadOnNewCharacters = false;

	private List<AttributeType> attributeBonus;

	private String rankBonusProgression;

	private String categoryBonusProgression;

	private EntityMetadata metadata;

}
