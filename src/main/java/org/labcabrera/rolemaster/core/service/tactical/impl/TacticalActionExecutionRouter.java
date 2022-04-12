package org.labcabrera.rolemaster.core.service.tactical.impl;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.MeleeAttackExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.MissileAttackExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class TacticalActionExecutionRouter {

	@Autowired
	private MeleeAttackExecutionService meleeExecutionService;

	@Autowired
	private MissileAttackExecutionService missileExecutionService;

	public Mono<TacticalAction> execute(TacticalAction action, TacticalActionExecution request) {
		if (action instanceof TacticalActionMeleeAttack) {
			if (!(request instanceof MeleeAttackExecution)) {
				throw new BadRequestException("Expected melee attack execution");
			}
			return meleeExecutionService.execute((TacticalActionMeleeAttack) action, (MeleeAttackExecution) request)
				.map(e -> (TacticalAction) e);
		}
		else if (action instanceof TacticalActionMissileAttack) {
			if (!(request instanceof MissileAttackExecution)) {
				throw new BadRequestException("Expected missile attack execution");
			}
			return missileExecutionService.execute((TacticalActionMissileAttack) action, (MissileAttackExecution) request)
				.map(e -> (TacticalAction) e);
		}
		throw new RuntimeException("Not implemented");
	}
}
