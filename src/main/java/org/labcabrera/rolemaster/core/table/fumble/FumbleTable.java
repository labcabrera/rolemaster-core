package org.labcabrera.rolemaster.core.table.fumble;

import java.util.Map;

import org.labcabrera.rolemaster.core.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.combat.FumbleTableResult;
import org.labcabrera.rolemaster.core.model.tactical.action.FumbleType;

import lombok.Data;

@Data
public class FumbleTable {

	private Map<FumbleType, Map<String, FumbleTableResult>> map;

	public FumbleTableResult get(FumbleType type, Integer roll) {
		Map<String, FumbleTableResult> tmp = map.get(type);
		int min;
		int max;
		String[] arr;
		for (String str : tmp.keySet()) {
			arr = str.split("-");
			min = Integer.parseInt(arr[0]);
			max = Integer.parseInt(arr[1]);
			if (roll >= min && roll <= max) {
				return tmp.get(str);
			}
		}
		throw new DataConsistenceException("Missing fumble result for " + type + " and roll " + roll);
	}
}
