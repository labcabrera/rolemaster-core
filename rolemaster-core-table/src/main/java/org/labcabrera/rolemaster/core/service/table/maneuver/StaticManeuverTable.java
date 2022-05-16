package org.labcabrera.rolemaster.core.service.table.maneuver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverResult;
import org.labcabrera.rolemaster.core.service.table.TableEntry;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
class StaticManeuverTable {

	private Map<String, StaticManeuverResult> results;

	public StaticManeuverResult getResult(int roll) {
		for (Entry<String, StaticManeuverResult> entry : results.entrySet()) {
			if (TableEntry.checkKeyRange(entry.getKey(), roll)) {
				return entry.getValue();
			}
		}
		throw new DataConsistenceException("Invalid table results for roll " + roll);
	}

	public boolean checkConsistence(int roll) {
		List<String> keys = new ArrayList<>();
		for (String key : results.keySet()) {
			if (TableEntry.checkKeyRange(key, roll)) {
				keys.add(key);
			}
		}
		if (keys.size() == 1) {
			return true;
		}
		log.warn("Invalid consistency {}. Keys: {}", roll, keys);
		return false;
	}

}
