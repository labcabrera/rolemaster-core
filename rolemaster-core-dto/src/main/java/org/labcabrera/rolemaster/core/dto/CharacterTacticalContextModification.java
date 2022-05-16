package org.labcabrera.rolemaster.core.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterTacticalContextModification {

	@Schema(description = "Number of HP added to the character. A negative value will subtract life points.", required = false, example = "7")
	private Integer hp;

	@Schema(description = "Number of PP added to the character. A negative value will subtract power points.", required = false, example = "0")
	private Integer pp;

	@Schema(description = "Number of PP added to the character. A negative value will subtract power points.", required = false, example = "0.00")
	private BigDecimal exhaustionPoints;

}
