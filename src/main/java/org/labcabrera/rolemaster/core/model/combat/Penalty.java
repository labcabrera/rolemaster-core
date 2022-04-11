package org.labcabrera.rolemaster.core.model.combat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Penalty {

	private Integer penalty;

	@Schema(description = "If this value is not present the penalty will last until the wound is healed.")
	private Integer rounds;

	@Schema(description = "Description of the reason for the penalty.")
	private String description;

}
