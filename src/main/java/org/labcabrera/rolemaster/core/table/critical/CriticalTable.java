package org.labcabrera.rolemaster.core.table.critical;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;

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
		for (String str : tmp.keySet()) {
			arr = str.split("-");
			min = Integer.parseInt(arr[0]);
			max = Integer.parseInt(arr[1]);
			if (roll >= min && roll <= max) {
				return tmp.get(str);
			}
		}
		//TODO
		throw new RuntimeException("Missing data");
	}

}
