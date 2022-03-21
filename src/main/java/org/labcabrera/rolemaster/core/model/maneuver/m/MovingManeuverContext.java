package org.labcabrera.rolemaster.core.model.maneuver.m;

import java.util.List;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovingManeuverContext {

	private MovingManeuverRequest request;

	private List<ManeuverModifier> modifiers;

}
