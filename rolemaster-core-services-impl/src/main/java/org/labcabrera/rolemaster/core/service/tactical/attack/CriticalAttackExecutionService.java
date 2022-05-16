package org.labcabrera.rolemaster.core.service.tactical.attack;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
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

		List<TacticalCriticalResult> pending = getPendingCriticalResults(tacticalAttack);
		List<String> keys = execution.getRolls().keySet().stream().sorted().toList();

		if (pending.size() != keys.size()) {
			throw new BadRequestException("Invalid critical rolls");
		}

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			TacticalCriticalResult tcr = pending.get(i);
			int roll = execution.getRolls().get(key);
			CriticalType type = tcr.getType();
			CriticalSeverity severity = tcr.getSeverity();
			CriticalTableResult result = criticalTable.getResult(type, severity, roll);
			tcr.setRoll(roll);
			tcr.setCriticalTableResult(result);
		}
		action.setState(TacticalActionState.PENDING_RESOLUTION);
		return action;
	}

	private List<TacticalCriticalResult> getPendingCriticalResults(TacticalActionAttack attack) {
		List<TacticalCriticalResult> results = new ArrayList<>();
		for (List<TacticalCriticalResult> list : attack.getCriticalResults().values()) {
			List<TacticalCriticalResult> unresolved = list.stream().filter(e -> e.getCriticalTableResult() == null).toList();
			results.addAll(unresolved);
		}
		return results;
	}

}
