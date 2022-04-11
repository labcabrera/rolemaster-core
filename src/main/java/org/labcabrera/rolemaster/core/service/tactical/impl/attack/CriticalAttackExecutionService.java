package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackCriticalExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.table.critical.CriticalTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriticalAttackExecutionService {

	@Autowired
	private CriticalTable criticalTable;

	public TacticalAction processCritical(TacticalAction action, MeleeAttackCriticalExecution execution) {
		if (action.getState() != TacticalActionState.PENDING_CRITICAL_RESOLUTION) {
			throw new BadRequestException("Invalid action state");
		}
		//TODO ranged/spell attack
		TacticalActionMeleeAttack meleeAttack = (TacticalActionMeleeAttack) action;

		CriticalType type = meleeAttack.getCriticalResult().getType();
		CriticalSeverity severity = meleeAttack.getCriticalResult().getSeverity();
		CriticalTableResult result = criticalTable.getResult(type, severity, execution.getRoll());

		meleeAttack.getCriticalResult().setRoll(execution.getRoll());
		meleeAttack.getCriticalResult().setCriticalTableResult(result);

		action.setState(TacticalActionState.PENDING_RESOLUTION);
		return action;
	}

}
