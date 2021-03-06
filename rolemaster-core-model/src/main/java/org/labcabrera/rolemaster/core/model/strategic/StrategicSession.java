package org.labcabrera.rolemaster.core.model.strategic;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.HasAuthorization;
import org.labcabrera.rolemaster.core.model.HasMetadata;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "strategicSessions")
@Schema(description = "A strategy session represents an adventure involving a list of characters. A strategy session can have several tactical sessions that represent the actions (e.g. combat or movement) of the characters and NPCs involved.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategicSession implements HasAuthorization, HasMetadata {

	@Schema(description = "Strategic session identifier.", required = true, example = "6242c18da7a9f7048331ca03")
	private String id;

	@Schema(description = "Strategic session name.", required = true, example = "Destruction of the ring Vol. 2")
	private String name;

	@Schema(description = "Rolemaster version.", required = true, example = "rmss")
	private RolemasterVersion version;

	@Schema(description = "User description.", required = false, example = "Adventure played by Foo and Bar for the destruction of the One Ring.")
	private String description;

	@Schema(description = "Universe", required = true, example = "middle-earth")
	private String universeId;

	@Schema(description = "Audit data.", required = true)
	@Builder.Default
	private EntityMetadata metadata = new EntityMetadata();

	@Schema(description = "Owner.", required = true, example = "rolemaster")
	private String owner;

}
