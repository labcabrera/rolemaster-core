package org.labcabrera.rolemaster.core.model.character;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "languages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Language {

	@Id
	private String id;

	private String name;

	private String group;

	private String universeId;

	private String description;

}
