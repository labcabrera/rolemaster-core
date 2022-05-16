package org.labcabrera.rolemaster.core.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

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
public class TacticalSessionCreation {

	@NotBlank
	@Schema(description = "Strategic session identifier.", required = true, example = "6241ee3947446c71b95d1db1")
	private String strategicSessionId;

	@NotBlank
	@Schema(description = "Tactical session name.", required = true, example = "Tactical session test")
	private String name;

	@Schema(description = "Scale factor for converting lengths (e.g. character movement) to board units such as centimeters or inches.", required = false, example = "1")
	@Builder.Default
	private Double scale = 1.0;

	@Builder.Default
	@Schema(description = "Type of terrain. If the value is not 'normal' it affects the fatigue point multiplier.", required = false, example = "normal")
	private TerrainType terrain = TerrainType.NORMAL;

	@Builder.Default
	@Schema(description = "Temperature in degrees Celsius. If the value is greater than 37 or less than -7 it affects the exhaustion point multiplier.", required = false, example = "24")
	private TemperatureMultiplier temperature = TemperatureMultiplier.NORMAL;

	@Builder.Default
	@Schema(description = "Customized multiplier to exhaustion points.", required = false, example = "1")
	private BigDecimal exhaustionMultiplier = BigDecimal.ONE;

	@Schema(description = "Bonus to be added to the weapon breakage roll in combat (so that we can prevent it from happening too often).", required = false, example = "0")
	private Integer breakageBonus;

	@Schema(description = "Tactical session name.", required = false, example = "Example of player's comment.")
	private String description;

}
