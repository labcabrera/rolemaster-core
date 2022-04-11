package org.labcabrera.rolemaster.core.service.tactical;

import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.FumbleExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;

import reactor.core.publisher.Mono;

public interface TacticalActionService {

	Mono<TacticalAction> delare(TacticalActionDeclaration request);

	Mono<TacticalAction> getDeclaredAction(String actionId);

	Mono<Void> removeDeclaredAction(String actionId);

	Mono<TacticalAction> execute(TacticalActionExecution request);

	Mono<TacticalAction> executeCritical(AttackCriticalExecution execution);

	Mono<TacticalAction> executeFumble(FumbleExecution execution);

}