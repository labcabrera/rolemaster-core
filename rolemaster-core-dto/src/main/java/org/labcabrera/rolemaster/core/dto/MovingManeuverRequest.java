package org.labcabrera.rolemaster.core.dto;

import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MovingManeuverRequest extends StaticManeuverRequest {

	@NotNull
	@Schema(description = "Maneuver difficulty.", example = "medium", required = true)
	private ManeuverDifficulty difficulty;

}
