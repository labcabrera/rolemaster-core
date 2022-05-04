package org.labcabrera.rolemaster.core.table.critical;

import java.util.Map;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.combat.LargeCreatureCriticalType;
import org.labcabrera.rolemaster.core.table.TableEntry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LargeCreatureCriticalTable {

	private CriticalType type;

	private Map<LargeCreatureCriticalType, Map<String, CriticalTableResult>> map;

	public CriticalTableResult get(LargeCreatureCriticalType severity, int roll) {
		if (roll < 1) {
			throw new BadRequestException("Invalid large critical creature roll.");
		}
		Map<String, CriticalTableResult> tmp = map.get(severity);
		for (String key : tmp.keySet()) {
			if (TableEntry.checkKeyRange(key, roll)) {
				return tmp.get(key);
			}
		}
		throw new DataConsistenceException("Invalid data for roll " + roll);
	}

}
