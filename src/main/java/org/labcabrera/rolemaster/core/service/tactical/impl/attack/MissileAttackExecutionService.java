package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMissileAttack;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MissileAttackExecutionService {

	public Mono<TacticalActionMeleeAttack> execute(TacticalActionMissileAttack action, MissileAttackExecution execution) {
		return null;
	}
}
