package org.labcabrera.rolemaster.core.model.tactical;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.service.tactical.TacticalSessionState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a sequence of rounds in which a combat usually takes place.")
@Document(collection = "tacticalSessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalSession {

	@Id
	@Schema(description = "Tactical session identifier.", required = true, example = "6242c18da7a9f7048331ca03")
	private String id;

	@Schema(description = "Strategic session identifier to which it belongs.", required = true, example = "6242c18da7a9f7048331ca08")
	private String strategicSessionId;

	@Schema(description = "Tactical session name.", required = true, example = "Combat in the meadows of Arnor")
	private String name;

	@Schema(description = "Tactical session description.", required = false, example = "Example of player's comment.")
	private String description;

	@Schema(description = "Tactical session state (open|closed).", required = true, example = "open")
	private TacticalSessionState state;

	@Builder.Default
	@Schema(description = "Current round. Zero if not initialized.", required = true, example = "42")
	private Integer currentRound = 0;

	@Builder.Default
	@Schema(description = "Audit data.", required = true)
	private EntityMetadata entityMetadata = new EntityMetadata();

}
