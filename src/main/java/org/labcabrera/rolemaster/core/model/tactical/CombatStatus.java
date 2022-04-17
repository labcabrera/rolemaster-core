package org.labcabrera.rolemaster.core.model.tactical;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.combat.Bleeding;
import org.labcabrera.rolemaster.core.model.combat.Penalty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CombatStatus {

	@Builder.Default
	private Map<Buff, Integer> buffs = new EnumMap<>(Buff.class);

	@Builder.Default
	private Map<Debuff, Integer> debuffs = new EnumMap<>(Debuff.class);

	@Builder.Default
	private List<Bleeding> bleeding = new ArrayList<>();

	@Builder.Default
	private List<Penalty> penalty = new ArrayList<>();

	@Builder.Default
	private List<AttackBonus> bonus = new ArrayList<>();

	@Builder.Default
	private Map<InjuryType, String> injuries = new EnumMap<>(InjuryType.class);

	@Deprecated
	@Builder.Default
	private List<String> otherEfects = new ArrayList<>();

	@Builder.Default
	@Schema(description = "Amount of round activity required to complete the missile weapon reload.")
	private Integer reloadingActivityPercent = 0;

	public Integer bleedingTotal() {
		return bleeding.stream().map(Bleeding::getHp).reduce(0, (a, b) -> a + b);
	}

	public CombatStatus addDebuff(Debuff key, Integer value) {
		if (debuffs.containsKey(key)) {
			int rounds = debuffs.get(key) + value;
			debuffs.put(key, rounds);
		}
		else {
			debuffs.put(key, value);
		}
		return this;
	}

}
