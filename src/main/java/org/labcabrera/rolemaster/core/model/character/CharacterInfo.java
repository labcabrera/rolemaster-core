package org.labcabrera.rolemaster.core.model.character;

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

	private Map<AttributeType, CharacterAttribute> attributes;

	private EntityMetadata metadata;

	private List<String> authorization;

}
