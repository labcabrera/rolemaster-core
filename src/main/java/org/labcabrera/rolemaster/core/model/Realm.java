package org.labcabrera.rolemaster.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "realms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Realm {

	@Id
	private String id;

	private String name;

}
