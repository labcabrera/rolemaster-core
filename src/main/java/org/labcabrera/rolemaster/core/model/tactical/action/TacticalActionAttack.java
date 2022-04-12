package org.labcabrera.rolemaster.core.model.tactical.action;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.OpenRoll;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public abstract class TacticalActionAttack extends TacticalAction {

	protected String target;

	@Schema(description = "In the case of a two-weapon attack, the target of the secondary weapon can be set.")
	protected String secondaryTarget;

	protected OpenRoll roll;

	protected OpenRoll secondaryRoll;

	@Builder.Default
	protected Map<String, Integer> offensiveBonusMap = new LinkedHashMap<>();

	@Builder.Default
	protected Map<String, Integer> defensiveBonusMap = new LinkedHashMap<>();

	protected AttackResult attackResult;

	protected AttackResult secondaryAttackResult;

	protected BigDecimal exhaustionPoints;

	public Integer getOffensiveBonus() {
		return offensiveBonusMap.values().stream().reduce(0, (a, b) -> a + b);
	}

	public Integer getDefensiveBonus() {
		return defensiveBonusMap.values().stream().reduce(0, (a, b) -> a + b);
	}

	public boolean isFlumbe() {
		if (attackResult != null && attackResult.getFumbleResult() != null) {
			return true;
		}
		else if (secondaryAttackResult != null && secondaryAttackResult.getCriticalResult() != null) {
			return true;
		}
		return false;
	}

	public boolean hasPendingCriticalResolution() {
		if (attackResult != null && attackResult.requiresCriticalResolution()) {
			return true;
		}
		return false;
	}
}
