package org.labcabrera.rolemaster.core.model.character;

import java.util.List;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "professions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of = "id")
public class Profession {

	@Schema(description = "Profession identifier")
	@Id
	private String id;

	@Schema(description = "Profession name")
	private String name;

	@Schema(description = "Available realms")
	private List<String> availableRealms;

	private EntityMetadata metadata;

}
