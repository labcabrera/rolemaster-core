package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.FumbleExecution;
import org.labcabrera.rolemaster.core.model.combat.FumbleTableResult;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackFumbleResult;
import org.labcabrera.rolemaster.core.model.tactical.action.FumbleType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.table.fumble.FumbleTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FumbleAttackExecutionService {

	@Autowired
	private FumbleTable fumbleTable;

	public TacticalActionAttack apply(TacticalActionAttack action, FumbleExecution execution) {
		AttackFumbleResult fumbleResult = getFirstUnresolvedFumble(action);
		FumbleType fumbleType = fumbleResult.getType();
		FumbleTableResult fumbleTableResult = fumbleTable.get(fumbleType, execution.getRoll());
		fumbleResult.setRoll(execution.getRoll());
		fumbleResult.setResult(fumbleTableResult);
		return action;
	}

	private AttackFumbleResult getFirstUnresolvedFumble(TacticalActionAttack action) {
		return action.getFumbleResults().stream().filter(e -> e.getResult() == null)
			.findFirst().orElseThrow();
	}
}
