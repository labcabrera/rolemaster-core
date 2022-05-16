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
public class Bleeding {

	private Integer hp;

	@Schema(description = "If not specified, bleeding will last until the wound heals.")
	private Integer rounds;

	private String description;

}
