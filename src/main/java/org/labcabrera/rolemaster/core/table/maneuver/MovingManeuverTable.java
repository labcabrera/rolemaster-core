package org.labcabrera.rolemaster.core.table.maneuver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.labcabrera.rolemaster.core.table.TableEntry;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class MovingManeuverTable {

	private Map<ManeuverDificulty, Map<String, MovingManeuverResult>> results;

	public MovingManeuverResult getResult(ManeuverDificulty dificulty, int roll) {
		if (dificulty == ManeuverDificulty.NONE) {
			throw new BadRequestException("Unexpected dificulty 'none'");
		}
		Map<String, MovingManeuverResult> map = results.get(dificulty);
		for (String key : map.keySet()) {
			if (TableEntry.checkKeyRange(key, roll)) {
				return map.get(key);
			}
		}
		throw new RuntimeException("Invalid data for roll " + roll);
	}

	public boolean checkConsistence(ManeuverDificulty dificulty, int roll) {
		Map<String, MovingManeuverResult> map = results.get(dificulty);
		List<String> keys = new ArrayList<>();
		for (String key : map.keySet()) {
			if (TableEntry.checkKeyRange(key, roll)) {
				keys.add(key);
			}
		}
		if (keys.size() == 1) {
			return true;
		}
		log.warn("Invalid consistency {} -> {}. Keys: {}", dificulty, roll, keys);
		return false;
	}

}
