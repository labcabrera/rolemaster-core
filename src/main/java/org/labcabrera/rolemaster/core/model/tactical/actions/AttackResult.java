package org.labcabrera.rolemaster.core.model.tactical.actions;

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

	@Schema(description = "Number of hit points caused by the attack.")
	private Integer hpResult;

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
