package org.labcabrera.rolemaster.core.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategicSessionCreation {

	@NotEmpty
	@Schema(description = "Session name.", required = true, example = "Demo session")
	private String name;

	@NotNull
	@Schema(description = "Rolemaster version.", required = true, example = "rmss")
	private RolemasterVersion version;

	@NotEmpty
	@Schema(description = "Universe", required = true, example = "middle-earth")
	private String universeId;

	@Schema(description = "Session name.", required = false, example = "Example of player's comment.")
	private String description;

}
