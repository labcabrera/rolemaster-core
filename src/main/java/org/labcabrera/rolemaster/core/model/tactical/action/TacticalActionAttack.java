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
	protected Map<AttackTargetType, AttackResult> attackResults = new EnumMap<>(AttackTargetType.class);

	private Map<AttackTargetType, List<TacticalCriticalResult>> criticalResults = new EnumMap<>(AttackTargetType.class);

	protected BigDecimal exhaustionPoints;

	public boolean isFlumbe() {
		return !fumbleResults.isEmpty();
	}

	public void addCriticalResult(TacticalCriticalResult critical, AttackTargetType attackTargetType) {
		if (!criticalResults.containsKey(attackTargetType)) {
			criticalResults.put(attackTargetType, new ArrayList<>());
		}
		criticalResults.get(attackTargetType).add(critical);
	}

	public boolean hasUnresolvedCritical() {
		for (List<TacticalCriticalResult> list : criticalResults.values()) {
			for (TacticalCriticalResult critical : list) {
				if (critical.getCriticalTableResult() == null) {
					return true;
				}
			}
		}
		return false;
	}

}
