package org.labcabrera.rolemaster.core.service.tactical;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.MovementExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.MovingManeuverExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.StaticManeuverExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovement;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovingManeuver;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionStaticManeuver;
import org.labcabrera.rolemaster.core.service.tactical.action.MovementExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.attack.MeleeAttackExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.attack.MissileAttackExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.maneuvers.MovingManeuverExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.maneuvers.StaticManeuverExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class TacticalActionExecutionRouter {

	@Autowired
	private MovementExecutionService movementExecutionService;

	@Autowired
	private MeleeAttackExecutionService meleeExecutionService;

	@Autowired
	private MissileAttackExecutionService missileExecutionService;

	@Autowired
	private MovingManeuverExecutionService movingManeuverExecutionService;

	@Autowired
	private StaticManeuverExecutionService staticManeuverExecutionService;

	public Mono<TacticalAction> execute(TacticalAction action, TacticalActionExecution request) {
		if (action instanceof TacticalActionMovement tacticalMovement) {
			if (!(request instanceof MovementExecution movementExecution)) {
				throw new BadRequestException("Expected movement execution.");
			}
			return movementExecutionService.execute(tacticalMovement, movementExecution).map(TacticalAction.class::cast);
		}
		else if (action instanceof TacticalActionMeleeAttack tacticalMeleeAction) {
			if (!(request instanceof MeleeAttackExecution meleeAttackExecution)) {
				throw new BadRequestException("Expected melee attack execution.");
			}
			return meleeExecutionService.execute(tacticalMeleeAction, meleeAttackExecution).map(TacticalAction.class::cast);
		}
		else if (action instanceof TacticalActionMissileAttack missileAttack) {
			if (!(request instanceof MissileAttackExecution missileAttackExecution)) {
				throw new BadRequestException("Expected missile attack execution.");
			}
			return missileExecutionService.execute(missileAttack, missileAttackExecution).map(TacticalAction.class::cast);
		}
		else if (action instanceof TacticalActionMovingManeuver mManeuver) {
			if (!(request instanceof MovingManeuverExecution mManeuverExecution)) {
				throw new BadRequestException("Expected moving maneuver execution.");
			}
			return movingManeuverExecutionService.execute(mManeuver, mManeuverExecution).map(TacticalAction.class::cast);
		}
		else if (action instanceof TacticalActionStaticManeuver sManeuver) {
			if (!(request instanceof StaticManeuverExecution sManeuverExecution)) {
				throw new BadRequestException("Expected static maneuver execution.");
			}
			return staticManeuverExecutionService.execute(sManeuver, sManeuverExecution).map(TacticalAction.class::cast);

		}
		throw new RuntimeException("Not implemented");
	}
}
