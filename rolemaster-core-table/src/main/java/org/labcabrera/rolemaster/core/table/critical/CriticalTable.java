package org.labcabrera.rolemaster.core.table.critical;

import java.util.Map;
import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriticalTable {

	private CriticalType type;

	private Map<CriticalSeverity, Map<String, CriticalTableResult>> map;

	public CriticalTableResult get(CriticalSeverity severity, int roll) {
		Map<String, CriticalTableResult> tmp = map.get(severity);
		int min;
		int max;
		String[] arr;
		for (Entry<String, CriticalTableResult> entry : tmp.entrySet()) {
			arr = entry.getKey().split("-");
			min = Integer.parseInt(arr[0]);
			max = Integer.parseInt(arr[1]);
			if (roll >= min && roll <= max) {
				return entry.getValue();
			}
		}
		throw new DataConsistenceException("Missing critical result for " + severity + " and roll " + roll);
	}

}
