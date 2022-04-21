package org.labcabrera.rolemaster.core.model.tactical.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.OpenRoll;

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

	protected Map<AttackTargetType, String> targets;

	protected Map<AttackTargetType, OpenRoll> rolls;

	@Builder.Default
	protected Map<AttackTargetType, Map<OffensiveBonusModifier, Integer>> offensiveBonusMap = new LinkedHashMap<>();

	@Builder.Default
	protected Map<AttackTargetType, AttackFumbleResult> fumbleResults = new EnumMap<>(AttackTargetType.class);

	@Builder.Default
	protected List<AttackResult> attackResults = new ArrayList<>();

	private List<TacticalCriticalResult> criticalResults = new ArrayList<>();

	protected BigDecimal exhaustionPoints;

	//	public Integer getOffensiveBonus() {
	//		return offensiveBonusMap.values().stream().reduce(0, (a, b) -> a + b);
	//	}

	public boolean isFlumbe() {
		return !fumbleResults.isEmpty();
	}

	public boolean hasPendingCriticalResolution() {
		return criticalResults.stream().filter(e -> e.getCriticalTableResult() == null).count() > 0L;
	}
}
