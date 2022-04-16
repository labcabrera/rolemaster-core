package org.labcabrera.rolemaster.core.table.maneuver;

import java.util.Map;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovingManeuverTable {

	private Map<ManeuverDificulty, Map<String, MovingManeuverResult>> results;

	public MovingManeuverResult getResult(ManeuverDificulty dificulty, int roll) {
		if (dificulty == ManeuverDificulty.NONE) {
			throw new BadRequestException("Unexpected dificulty 'none'");
		}
		Map<String, MovingManeuverResult> map = results.get(dificulty);
		for (String key : map.keySet()) {
			if (check(key, roll)) {
				return map.get(key);
			}
		}
		throw new RuntimeException("Invalid data for roll " + roll);
	}

	private boolean check(String key, int roll) {
		return false;
	}

}
