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
	private Integer attackResult;

	private String weaponTableId;

	private Integer targetArmor;

	@Schema(description = "Number of hit points caused by the attack.")
	private Integer hp;

	private AttackFumbleResult fumbleResult;

	@Schema(description = "In case the attack provokes a critical (or more than one) define the information related to the result of the critical.")
	private TacticalCriticalResult criticalResult;

	public boolean isCritical() {
		return criticalResult != null;
	}

	public boolean requiresCriticalResolution() {
		return criticalResult != null && criticalResult.getCriticalTableResult() == null;
	}

}
