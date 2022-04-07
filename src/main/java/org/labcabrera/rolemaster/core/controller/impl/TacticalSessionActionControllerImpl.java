package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.TacticalSessionActionController;
import org.labcabrera.rolemaster.core.controller.converter.TacticalActionConverter;
import org.labcabrera.rolemaster.core.dto.actions.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class TacticalSessionActionControllerImpl implements TacticalSessionActionController {

	@Autowired
	private TacticalService tacticalService;

	@Autowired
	private TacticalActionConverter actionConverter;

	@Autowired
	private TacticalRoundRepository roundRepository;

	@Override
	public Mono<TacticalRound> getDeclaredAction(String tacticalSessionId, String source, TacticalActionPhase phase) {
		return tacticalService.getCurrentRound(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session " + tacticalSessionId + " not found")))
			.map(round -> {
				TacticalAction action = round.getActions().stream()
					.filter(e -> source.equals(e.getSource()) && phase.equals(e.getPriority()))
					.findFirst().orElse(null);
				if (action == null) {
					throw new BadRequestException("Round has not any action for " + source + " and priority " + phase);
				}
				return round;
			});
	}

	@Override
	public Mono<TacticalRound> removeDeclaredAction(String tacticalSessionId, String source, TacticalActionPhase phase) {
		return tacticalService.getCurrentRound(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session " + tacticalSessionId + " not found")))
			.map(round -> {
				TacticalAction action = round.getActions().stream()
					.filter(e -> source.equals(e.getSource()) && phase.equals(e.getPriority()))
					.findFirst().orElse(null);
				if (action == null) {
					throw new BadRequestException("Round has not any action for " + source + " and priority " + phase);
				}
				round.getActions().remove(action);
				return round;
			})
			.flatMap(roundRepository::save);
	}

	@Override
	public Mono<TacticalRound> delare(String id, @Valid TacticalActionDeclaration actionDeclaration) {
		TacticalAction tacticalAction = actionConverter.convert(actionDeclaration);
		return tacticalService.declare(id, tacticalAction);
	}

}
