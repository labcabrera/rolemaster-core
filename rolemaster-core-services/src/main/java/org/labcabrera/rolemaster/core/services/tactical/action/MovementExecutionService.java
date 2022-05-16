package org.labcabrera.rolemaster.core.services.tactical.action;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.dto.action.execution.MovementExecution;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovement;

import reactor.core.publisher.Mono;

public interface MovementExecutionService {

	Mono<TacticalAction> execute(@NotNull TacticalActionMovement tacticalMovement, @NotNull @Valid MovementExecution movementExecution);

}