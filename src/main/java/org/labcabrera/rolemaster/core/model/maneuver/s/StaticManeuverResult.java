package org.labcabrera.rolemaster.core.model.maneuver.s;

import java.util.ArrayList;
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
public class StaticManeuverResult {

	private String description;

	@Builder.Default
	private List<ManeuverModifier> modifiers = new ArrayList<>();

}
