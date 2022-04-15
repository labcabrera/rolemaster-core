package org.labcabrera.rolemaster.core.table.critical;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;

class CriticalTableSTest {

	private CriticalTableS table = new CriticalTableS();

	@Test
	void test() {
		for (CriticalSeverity severity : CriticalSeverity.values()) {
			for (int i = 1; i <= 100; i++) {
				CriticalTableResult result = table.getResult(severity, i);
				assertNotNull(result);
			}
		}
	}

}
