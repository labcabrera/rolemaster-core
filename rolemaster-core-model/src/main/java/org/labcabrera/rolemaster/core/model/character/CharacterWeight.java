package org.labcabrera.rolemaster.core.model.character;

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
public class CharacterWeight {

	private Integer weight;

	@Builder.Default
	private Integer weightPenalty = 0;

	@Schema(description = "All of the character’s equipment and supplies other than clothes and armor.")
	@Builder.Default
	private BigDecimal itemWeight = BigDecimal.ZERO;

	@Schema(description = "All of the character's equipement.")
	@Builder.Default
	private BigDecimal totalCarriedWeight = BigDecimal.ZERO;

}
