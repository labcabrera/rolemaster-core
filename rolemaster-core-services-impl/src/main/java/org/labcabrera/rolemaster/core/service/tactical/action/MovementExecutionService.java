package org.labcabrera.rolemaster.core.service.tactical.action;

import org.labcabrera.rolemaster.core.dto.action.execution.MovementExecution;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovement;

import reactor.core.publisher.Mono;

public interface MovementExecutionService {

	Mono<TacticalAction> execute(TacticalActionMovement tacticalMovement, MovementExecution movementExecution);

}