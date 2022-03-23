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
public class SessionUpdateRequest {

	@Schema(description = "Session name.", required = true, example = "Demo session")
	private String name;

}