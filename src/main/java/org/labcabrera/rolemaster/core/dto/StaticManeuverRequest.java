package org.labcabrera.rolemaster.core.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StaticManeuverRequest {

	@NotNull
	@Schema(description = "Maneuver roll.", example = "42", required = true)
	private Integer roll;

	@Builder.Default
	@Schema(description = "Maneuver modifiers.", required = false)
	private Map<String, Integer> modifiers = new LinkedHashMap<>();

}
