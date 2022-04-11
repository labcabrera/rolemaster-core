package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.labcabrera.rolemaster.core.controller.TacticalSessionActionController;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.service.tactical.TacticalActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class TacticalSessionActionControllerImpl implements TacticalSessionActionController {

	@Autowired
	private TacticalActionService tacticalActionService;

	@Override
	public Mono<TacticalRound> getDeclaredAction(String tacticalSessionId, String source, String priority) {
		TacticalActionPhase priorityConverted = parse(priority);
		return tacticalActionService.getDeclaredAction(tacticalSessionId, source, priorityConverted);
	}

	@Override
	public Mono<TacticalRound> removeDeclaredAction(String tacticalSessionId, String source, String priority) {
		TacticalActionPhase priorityConverted = parse(priority);
		return tacticalActionService.removeDeclaredAction(tacticalSessionId, source, priorityConverted);
	}

	@Override
	public Mono<TacticalRound> delare(@NotEmpty String tacticalSessionId, @Valid TacticalActionDeclaration actionDeclaration) {
		return tacticalActionService.delare(tacticalSessionId, actionDeclaration);
	}

	private TacticalActionPhase parse(String value) {
		return TacticalActionPhase.valueOf(value.toUpperCase());
	}

}
