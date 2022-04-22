package org.labcabrera.rolemaster.core.service.tactical.impl;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.MovingManeuverExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovingManeuver;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.MeleeAttackExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.MissileAttackExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers.MovingManeuverExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class TacticalActionExecutionRouter {

	@Autowired
	private MeleeAttackExecutionService meleeExecutionService;

	@Autowired
	private MissileAttackExecutionService missileExecutionService;

	@Autowired
	private MovingManeuverExecutionService movingManeuverExecutionService;

	public Mono<TacticalAction> execute(TacticalAction action, TacticalActionExecution request) {
		if (action instanceof TacticalActionMeleeAttack tacticalMeleeAction) {
			if (!(request instanceof MeleeAttackExecution meleeAttackExecution)) {
				throw new BadRequestException("Expected melee attack execution");
			}
			return meleeExecutionService.execute(tacticalMeleeAction, meleeAttackExecution).map(TacticalAction.class::cast);
		}
		else if (action instanceof TacticalActionMissileAttack missileAttack) {
			if (!(request instanceof MissileAttackExecution missileAttackExecution)) {
				throw new BadRequestException("Expected missile attack execution");
			}
			return missileExecutionService.execute(missileAttack, missileAttackExecution).map(TacticalAction.class::cast);
		}
		else if (action instanceof TacticalActionMovingManeuver movingManeuver) {
			if (!(request instanceof MovingManeuverExecution movingManeuverExecution)) {
				throw new BadRequestException("Expected moving maneuver execution");
			}
			return movingManeuverExecutionService.execute(movingManeuver, movingManeuverExecution).map(TacticalAction.class::cast);
		}
		throw new RuntimeException("Not implemented");
	}
}
