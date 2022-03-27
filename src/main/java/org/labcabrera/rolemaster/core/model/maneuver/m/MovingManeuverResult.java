package org.labcabrera.rolemaster.core.model.maneuver.m;

import java.util.ArrayList;
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

	@Builder.Default
	private List<ManeuverModifier> modifiers = new ArrayList<>();

	public Integer getTotalModifiers() {
		int result = 0;
		for (ManeuverModifier modifier : modifiers) {
			result += modifier.getModifier();
		}
		return result;
	}

}
