package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.TacticalSessionActionController;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.service.tactical.TacticalActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class TacticalSessionActionControllerImpl implements TacticalSessionActionController {

	@Autowired
	private TacticalActionService tacticalActionService;

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

}
