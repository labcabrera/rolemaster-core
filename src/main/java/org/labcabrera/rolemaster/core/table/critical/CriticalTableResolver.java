package org.labcabrera.rolemaster.core.table.critical;

import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;

public interface CriticalTableResolver {

	CriticalType getType();

	CriticalTableResult getResult(CriticalSeverity severity, Integer roll);

}
