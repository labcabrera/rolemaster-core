package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.TacticalSessionActionController;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
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
	public Mono<TacticalAction> declare(@Valid TacticalActionDeclaration actionDeclaration) {
		return tacticalActionService.delare(actionDeclaration);
	}

	@Override
	public Flux<TacticalAction> findActionsByRound(String roundId) {
		return tacticalActionsRepository.findByRoundId(roundId);
	}

	@Override
	public Mono<TacticalAction> declare(String actionId, @Valid TacticalActionExecution actionDeclaration) {
		return tacticalActionService.execute(actionId, actionDeclaration);
	}

}
