package org.labcabrera.rolemaster.core.model.tactical.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttackResult {

	@Schema(description = "Result of the attack plus all modifiers.")
	private Integer result;

	private Integer totalBonus;

	private String weaponTableId;

	private Integer targetArmor;

	@Schema(description = "Number of hit points caused by the attack.")
	private Integer hp;

}
