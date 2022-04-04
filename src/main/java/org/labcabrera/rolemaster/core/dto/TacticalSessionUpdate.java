package org.labcabrera.rolemaster.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalSessionUpdate {

	@Schema(description = "Tactical session name.", required = false, example = "Demo session")
	private String name;

	@Schema(description = "Tactical session description.", required = false, example = "Some user description.")
	private String description;

}
