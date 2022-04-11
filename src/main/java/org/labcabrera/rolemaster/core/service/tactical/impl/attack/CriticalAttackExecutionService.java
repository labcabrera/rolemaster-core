package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackCriticalExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionAttack;
import org.labcabrera.rolemaster.core.table.critical.CriticalTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service updates the data of a critical attack from the (closed) critical roll.
 */
@Service
public class CriticalAttackExecutionService {

	@Autowired
	private CriticalTable criticalTable;

	public TacticalAction apply(TacticalAction action, MeleeAttackCriticalExecution execution) {
		if (action.getState() != TacticalActionState.PENDING_CRITICAL_RESOLUTION) {
			throw new BadRequestException("Invalid action state " + action.getState());
		}
		else if (!(action instanceof TacticalActionAttack)) {
			throw new BadRequestException("Invalid action type " + action.getClass().getSimpleName());
		}
		TacticalActionAttack tacticalAttack = (TacticalActionAttack) action;

		CriticalType type = tacticalAttack.getCriticalResult().getType();
		CriticalSeverity severity = tacticalAttack.getCriticalResult().getSeverity();
		CriticalTableResult result = criticalTable.getResult(type, severity, execution.getRoll());

		tacticalAttack.getCriticalResult().setRoll(execution.getRoll());
		tacticalAttack.getCriticalResult().setCriticalTableResult(result);

		action.setState(TacticalActionState.PENDING_RESOLUTION);
		return action;
	}

}
