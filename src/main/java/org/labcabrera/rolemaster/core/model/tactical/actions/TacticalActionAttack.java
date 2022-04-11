package org.labcabrera.rolemaster.core.model.tactical.actions;

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

	private String target;

	private OpenRoll roll;

	@Builder.Default
	private Map<String, Integer> offensiveBonusMap = new LinkedHashMap<>();

	@Builder.Default
	private Map<String, Integer> defensiveBonusMap = new LinkedHashMap<>();

	private String weaponId;

	@Schema(description = "Result of the attack plus all modifiers.")
	private Integer attackResult;

	@Schema(description = "Number of hit points caused by the attack.")
	private Integer hpResult;

	@Schema(description = "In case the attack provokes a critical (or more than one) define the information related to the result of the critical.")
	private TacticalCriticalResult criticalResult;

	private BigDecimal exhaustionPoints;

	public Integer getOffensiveBonus() {
		return offensiveBonusMap.values().stream().reduce(0, (a, b) -> a + b);
	}

	public Integer getDefensiveBonus() {
		return defensiveBonusMap.values().stream().reduce(0, (a, b) -> a + b);
	}
}
