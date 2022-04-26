package org.labcabrera.rolemaster.core.model.tactical;

import java.math.BigDecimal;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
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
	@Schema(description = "Type of terrain. If the value is not 'normal' it affects the fatigue point multiplier.", required = false, example = "normal")
	private TerrainType terrain = TerrainType.NORMAL;

	@Schema(description = "Temperature in degrees Celsius. If the value is greater than 37 or less than -7 it affects the exhaustion point multiplier.", required = false, example = "24")
	private TemperatureMultiplier temperature;

	@Schema(description = "Customized multiplier to exhaustion points.", required = false, example = "1")
	private BigDecimal exhaustionMultiplier;

	@Schema(description = "Bonus to be added to the weapon breakage roll in combat (so that we can prevent it from happening too often).", required = false, example = "0")
	private Integer breakageBonus;

	@Builder.Default
	@Schema(description = "Audit data.", required = true)
	private EntityMetadata entityMetadata = new EntityMetadata();

}
