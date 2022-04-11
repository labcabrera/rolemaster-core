package org.labcabrera.rolemaster.core.service.tactical;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.labcabrera.rolemaster.core.controller.converter.TacticalActionConverter;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalActionService {

	@Autowired
	private TacticalService tacticalService;

	@Autowired
	private TacticalActionConverter actionConverter;

	@Autowired
	private TacticalRoundRepository roundRepository;

	public Mono<TacticalRound> getDeclaredAction(String tacticalSessionId, String source, TacticalActionPhase priority) {
		return tacticalService.getCurrentRound(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session " + tacticalSessionId + " not found")))
			.map(round -> {
				TacticalAction action = round.getActions().stream()
					.filter(e -> source.equals(e.getSource()) && priority.equals(e.getPriority()))
					.findFirst().orElse(null);
				if (action == null) {
					throw new BadRequestException("Round has not any action for " + source + " and priority " + priority);
				}
				return round;
			});
	}

	public Mono<TacticalRound> removeDeclaredAction(String tacticalSessionId, String source, TacticalActionPhase priority) {
		return tacticalService.getCurrentRound(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session " + tacticalSessionId + " not found")))
			.map(round -> {
				TacticalAction action = round.getActions().stream()
					.filter(e -> source.equals(e.getSource()) && priority.equals(e.getPriority()))
					.findFirst().orElse(null);
				if (action == null) {
					throw new BadRequestException("Missing action for " + source + " and priority " + priority);
				}
				round.getActions().remove(action);
				return round;
			})
			.flatMap(roundRepository::save);
	}

	public Mono<TacticalRound> delare(@NotEmpty String tacticalSessionId, @Valid TacticalActionDeclaration actionDeclaration) {
		TacticalAction tacticalAction = actionConverter.convert(actionDeclaration);
		return tacticalService.declare(tacticalSessionId, tacticalAction);
	}

	public void setInitiative(String tacticalSessionId, String tctx, int initiativeRoll) {
		// TODO Auto-generated method stub

	}

	public Mono<TacticalRound> execute(String tsId, String id, TacticalActionPhase normal, TacticalActionExecution execution) {
		return null;
	}

	public void startExecutionPhase(String tsId) {
		// TODO Auto-generated method stub

	}
}
