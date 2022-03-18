package org.labcabrera.rolemaster.core.model.smaneuver;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaticManeuverResult {

	@Builder.Default
	private List<StaticManeuverModifier> modifiers = new ArrayList<>();

}
