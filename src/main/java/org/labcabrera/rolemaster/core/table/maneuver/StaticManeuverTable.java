package org.labcabrera.rolemaster.core.table.maneuver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.table.TableEntry;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class StaticManeuverTable {

	private Map<String, StaticManeuverResult> results;

	public StaticManeuverResult getResult(int roll) {
		for (String key : results.keySet()) {
			if (TableEntry.checkKeyRange(key, roll)) {
				return results.get(key);
			}
		}
		throw new RuntimeException("Invalid table results for roll " + roll);
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
