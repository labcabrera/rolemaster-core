package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.FumbleExecution;
import org.labcabrera.rolemaster.core.model.combat.FumbleTableResult;
import org.labcabrera.rolemaster.core.model.tactical.actions.FumbleType;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionAttack;
import org.labcabrera.rolemaster.core.table.fumble.FumbleTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FumbleAttackExecutionService {

	@Autowired
	private FumbleTable fumbleTable;

	public TacticalActionAttack apply(TacticalActionAttack action, FumbleExecution execution) {
		FumbleType fumbleType = action.getFumbleResult().getType();
		FumbleTableResult fumbleTableResult = fumbleTable.get(fumbleType, execution.getRoll());
		action.getFumbleResult().setResult(fumbleTableResult);
		return action;
	}
}
