package org.labcabrera.rolemaster.core.table.maneuver;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaticManeuverTable {

	private Map<String, StaticManeuverResult> results;

	public StaticManeuverResult getResult(int roll) {
		for (String key : results.keySet()) {
			if (check(key, roll)) {
				return results.get(key);
			}
		}
		throw new RuntimeException("Invalid table results for roll " + roll);
	}

	private boolean check(String key, int roll) {
		if (key.startsWith("<")) {
			int min = Integer.valueOf(key.substring(1));
			return roll < min;

		}
		else if (key.startsWith(">")) {
			int max = Integer.valueOf(key.substring(1));
			return roll > max;
		}
		String[] tmp = key.split(":");
		int min = Integer.valueOf(tmp[0]);
		int max = Integer.valueOf(tmp[1]);
		return roll >= min && roll <= max;
	}
}
