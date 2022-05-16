package org.labcabrera.rolemaster.core.services.rmss.tactical.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.FumbleExecution;
import org.labcabrera.rolemaster.core.model.combat.FumbleTableResult;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackFumbleResult;
import org.labcabrera.rolemaster.core.model.tactical.action.FumbleType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.table.rmss.fumble.FumbleTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FumbleAttackExecutionService {

	@Autowired
	private FumbleTableService fumbleTableService;

	public TacticalActionAttack apply(TacticalActionAttack action, FumbleExecution execution) {
		AttackFumbleResult fumbleResult = getFirstUnresolvedFumble(action);
		FumbleType fumbleType = fumbleResult.getType();
		FumbleTableResult fumbleTableResult = fumbleTableService.getResult(fumbleType, execution.getRoll());
		fumbleResult.setRoll(execution.getRoll());
		fumbleResult.setResult(fumbleTableResult);
		return action;
	}

	private AttackFumbleResult getFirstUnresolvedFumble(TacticalActionAttack action) {
		return action.getFumbleResults().values().stream().filter(e -> e.getResult() == null)
			.findFirst().orElseThrow();
	}
}
