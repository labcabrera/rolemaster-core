package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.TacticalSessionActionController;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.FumbleExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.WeaponBreakageExecution;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TacticalSessionActionControllerImpl implements TacticalSessionActionController {

	@Autowired
	private TacticalActionService tacticalActionService;

	@Autowired
	private TacticalActionRepository tacticalActionsRepository;

	@Override
	public Mono<TacticalAction> getDeclaredAction(String actionId) {
		return tacticalActionService.getDeclaredAction(actionId);
	}

	@Override
	public Mono<Void> removeDeclaredAction(String actionId) {
		return tacticalActionService.removeDeclaredAction(actionId);
	}

	@Override
	public Mono<TacticalAction> declare(TacticalActionDeclaration actionDeclaration) {
		return tacticalActionService.delare(actionDeclaration);
	}

	@Override
	public Flux<TacticalAction> findActionsByRound(String roundId) {
		return tacticalActionsRepository.findByRoundId(roundId);
	}

	@Override
	public Mono<TacticalAction> execute(String actionId, TacticalActionExecution actionDeclaration) {
		return tacticalActionService.execute(actionId, actionDeclaration);
	}

	@Override
	public Mono<TacticalAction> executeCritical(String actionId, AttackCriticalExecution request) {
		return tacticalActionService.executeCritical(actionId, request);
	}

	@Override
	public Mono<TacticalAction> executeFumble(String actionId, FumbleExecution request) {
		return tacticalActionService.executeFumble(actionId, request);
	}

	@Override
	public Mono<TacticalAction> executeBreakage(String actionId, WeaponBreakageExecution request) {
		return tacticalActionService.executeBreakage(actionId, request);
	}

}
