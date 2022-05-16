package org.labcabrera.rolemaster.core.model.maneuver;

import java.util.EnumMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.combat.Bleeding;
import org.labcabrera.rolemaster.core.model.combat.Penalty;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.InjuryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovingManeuverResult {

	private Integer result;

	private Integer hp;

	@Builder.Default
	private Map<Debuff, Integer> debuffs = new EnumMap<>(Debuff.class);

	@Builder.Default
	private Map<InjuryType, String> injuries = new EnumMap<>(InjuryType.class);

	private Penalty penalty;

	private Bleeding bleeding;

	private String text;

}
