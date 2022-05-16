package org.labcabrera.rolemaster.core.table.maneuver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;
import org.labcabrera.rolemaster.core.model.maneuver.MovingManeuverResult;
import org.labcabrera.rolemaster.core.table.TableEntry;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
class MovingManeuverTable {

	private Map<ManeuverDifficulty, Map<String, MovingManeuverResult>> results;

	public MovingManeuverResult getResult(ManeuverDifficulty difficulty, int roll) {
		if (difficulty == ManeuverDifficulty.NONE) {
			throw new BadRequestException("Unexpected difficulty 'none'");
		}
		Map<String, MovingManeuverResult> map = results.get(difficulty);
		for (Entry<String, MovingManeuverResult> entry : map.entrySet()) {
			if (TableEntry.checkKeyRange(entry.getKey(), roll)) {
				return entry.getValue();
			}
		}
		throw new DataConsistenceException("Invalid data for roll " + roll);
	}

	public boolean checkConsistence(ManeuverDifficulty difficulty, int roll) {
		Map<String, MovingManeuverResult> map = results.get(difficulty);
		List<String> keys = new ArrayList<>();
		for (String key : map.keySet()) {
			if (TableEntry.checkKeyRange(key, roll)) {
				keys.add(key);
			}
		}
		if (keys.size() == 1) {
			return true;
		}
		log.warn("Invalid consistency {} -> {}. Keys: {}", difficulty, roll, keys);
		return false;
	}

}
