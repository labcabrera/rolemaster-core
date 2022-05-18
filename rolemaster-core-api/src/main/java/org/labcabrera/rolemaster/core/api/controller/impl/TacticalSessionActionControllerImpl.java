package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.TacticalSessionActionController;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.FumbleExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.WeaponBreakageExecution;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.services.tactical.TacticalActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
	public Mono<TacticalAction> getDeclaredAction(JwtAuthenticationToken auth, String actionId) {
		return tacticalActionService.getDeclaredAction(actionId);
	}

	@Override
	public Mono<Void> removeDeclaredAction(JwtAuthenticationToken auth, String actionId) {
		return tacticalActionService.removeDeclaredAction(actionId);
	}

	@Override
	public Mono<TacticalAction> declare(JwtAuthenticationToken auth, TacticalActionDeclaration actionDeclaration) {
		return tacticalActionService.delare(actionDeclaration);
	}

	@Override
	public Flux<TacticalAction> findActionsByRound(JwtAuthenticationToken auth, String roundId) {
		return tacticalActionsRepository.findByRoundId(roundId);
	}

	@Override
	public Mono<TacticalAction> execute(JwtAuthenticationToken auth, String actionId, TacticalActionExecution actionDeclaration) {
		return tacticalActionService.execute(auth, actionId, actionDeclaration);
	}

	@Override
	public Mono<TacticalAction> executeCritical(JwtAuthenticationToken auth, String actionId, AttackCriticalExecution request) {
		return tacticalActionService.executeCritical(actionId, request);
	}

	@Override
	public Mono<TacticalAction> executeFumble(JwtAuthenticationToken auth, String actionId, FumbleExecution request) {
		return tacticalActionService.executeFumble(actionId, request);
	}

	@Override
	public Mono<TacticalAction> executeBreakage(JwtAuthenticationToken auth, String actionId, WeaponBreakageExecution request) {
		return tacticalActionService.executeBreakage(auth, actionId, request);
	}

}
