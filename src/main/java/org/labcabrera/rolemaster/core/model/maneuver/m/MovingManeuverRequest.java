package org.labcabrera.rolemaster.core.model.maneuver.m;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovingManeuverRequest {

	private String characterStatusId;

	private ManeuverDificulty dificulty;

	@Builder.Default
	private List<ManeuverModifier> customModifiers = new ArrayList<>();

}
