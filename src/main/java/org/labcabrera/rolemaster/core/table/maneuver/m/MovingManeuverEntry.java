package org.labcabrera.rolemaster.core.table.maneuver.m;

import org.labcabrera.rolemaster.core.model.character.status.CharacterStatusModifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovingManeuverEntry implements CharacterStatusModifier {

	private String text;

	private Integer result;

	private Integer hp;

	private Integer stunned;

	private Integer unconscious;

}
