package org.labcabrera.rolemaster.core.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalSessionCreation {

	@NotBlank
	@Schema(description = "Strategic session identifier.", required = true, example = "6241ee3947446c71b95d1db1")
	private String strategicSessionId;

	@NotBlank
	@Schema(description = "Tactical session name.", required = true, example = "Tactical session test")
	private String name;

	@Schema(description = "Tactical session name.", required = false, example = "Example of player's comment.")
	private String description;

}
