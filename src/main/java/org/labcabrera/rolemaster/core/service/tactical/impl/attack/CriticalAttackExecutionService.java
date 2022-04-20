package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalCriticalResult;
import org.labcabrera.rolemaster.core.table.critical.CriticalTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service updates the data of a critical attack from the (closed) critical roll.
 */
@Service
public class CriticalAttackExecutionService {

	@Autowired
	private CriticalTableService criticalTable;

	public TacticalAction apply(TacticalAction action, AttackCriticalExecution execution) {
		if (action.getState() != TacticalActionState.PENDING_CRITICAL_RESOLUTION) {
			throw new BadRequestException("Invalid action state " + action.getState());
		}
		else if (!(action instanceof TacticalActionAttack)) {
			throw new BadRequestException("Invalid action type " + action.getClass().getSimpleName());
		}
		TacticalActionAttack tacticalAttack = (TacticalActionAttack) action;

		TacticalCriticalResult tcr = getFirstUnresolvedCritical(tacticalAttack);
		CriticalType type = tcr.getType();
		CriticalSeverity severity = tcr.getSeverity();
		CriticalTableResult result = criticalTable.getResult(type, severity, execution.getRoll());

		tcr.setRoll(execution.getRoll());
		tcr.setCriticalTableResult(result);

		if (!tacticalAttack.hasPendingCriticalResolution()) {
			action.setState(TacticalActionState.PENDING_RESOLUTION);
		}
		return action;
	}

	private TacticalCriticalResult getFirstUnresolvedCritical(TacticalActionAttack tacticalAttack) {
		return tacticalAttack.getCriticalResults().stream()
			.filter(e -> e.getRoll() == null)
			.findFirst().orElseThrow(() -> new BadRequestException("Not found any unresolved critical"));
	}

}
