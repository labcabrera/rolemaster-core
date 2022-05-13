package org.labcabrera.rolemaster.core.dto;

import java.math.BigDecimal;

import org.labcabrera.rolemaster.core.model.tactical.TemperatureMultiplier;
import org.labcabrera.rolemaster.core.model.tactical.TerrainType;

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

	@Schema(description = "Scale factor for converting lengths (e.g. character movement) to board units such as centimeters or inches.", required = false, example = "1")
	@Builder.Default
	private Float scale = 1f;

	@Builder.Default
	@Schema(description = "Type of terrain. If the value is not 'normal' it affects the fatigue point multiplier.", required = false, example = "normal")
	private TerrainType terrain = TerrainType.NORMAL;

	@Schema(description = "Temperature in degrees Celsius. If the value is greater than 37 or less than -7 it affects the exhaustion point multiplier.", required = false, example = "24")
	private TemperatureMultiplier temperature;

	@Schema(description = "Customized multiplier to exhaustion points.", required = false, example = "1")
	private BigDecimal exhaustionMultiplier;

	@Schema(description = "Tactical session description.", required = false, example = "Some user description.")
	private String description;

}
