package org.labcabrera.rolemaster.core.model.combat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.AttackBonus;
import org.labcabrera.rolemaster.core.model.tactical.Buff;
import org.labcabrera.rolemaster.core.model.tactical.CharacterStatusModifier;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.InjuryType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a result of a given critical table.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriticalTableResult implements CharacterStatusModifier {

	private Integer hp;

	private Penalty penalty;

	private Integer bleeding;

	private AttackBonus bonus;

	@Builder.Default
	private Map<Buff, Integer> buffs = new LinkedHashMap<>();

	@Builder.Default
	private Map<Debuff, Integer> debuffs = new LinkedHashMap<>();

	@Builder.Default
	private Map<Debuff, Integer> attackerDebuffs = new LinkedHashMap<>();

	@Builder.Default
	private Map<ConditionalCriticalEffect, CriticalTableResult> conditionalEffects = new LinkedHashMap<>();

	@Builder.Default
	private Map<InjuryType, String> injuries = new LinkedHashMap<>();

	@Builder.Default
	private List<String> otherEffects = new ArrayList<>();

	private String text;

}
