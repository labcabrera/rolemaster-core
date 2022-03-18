package org.labcabrera.rolemaster.core.model.character;

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

	private String skillCategoryId;

	private String name;

}
