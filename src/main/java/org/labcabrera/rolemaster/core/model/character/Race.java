package org.labcabrera.rolemaster.core.model.character;

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

	private Map<AttributeType, Integer> attributeModifiers;

	private Map<String, Integer> resistanceModifiers;

}
