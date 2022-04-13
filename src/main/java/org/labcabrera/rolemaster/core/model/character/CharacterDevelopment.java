package org.labcabrera.rolemaster.core.model.character;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterDevelopment {

	@Schema(description = "Skill development poitns (Ag + Co + Me + Re + Sd) ÷ 5")
	private Integer totalPoints;

	@Builder.Default
	private Integer usedPoints = 0;

}
