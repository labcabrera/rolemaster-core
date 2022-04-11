package org.labcabrera.rolemaster.core.table.critical;

import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CriticalTable {

	@Autowired
	private CriticalTableS criticalTableS;

	public CriticalTableResult getResult(CriticalType type, CriticalSeverity severity, Integer roll) {
		switch (type) {
		case S:
			return criticalTableS.getResult(severity, roll);
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

}
