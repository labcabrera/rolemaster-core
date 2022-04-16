package org.labcabrera.rolemaster.core.table.maneuver;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.CharacterStatusModifier;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovingManeuverResult implements CharacterStatusModifier {

	private Integer result;

	private Integer hp;

	private Map<Debuff, Integer> debuffs;

	private String text;

}
