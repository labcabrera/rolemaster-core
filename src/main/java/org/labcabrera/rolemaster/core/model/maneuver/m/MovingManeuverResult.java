package org.labcabrera.rolemaster.core.model.maneuver.m;

import java.util.List;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovingManeuverResult {

	private Integer requestResult;

	private List<ManeuverModifier> modifiers;

}
