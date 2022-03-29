package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.TacticalSessionController;
import org.labcabrera.rolemaster.core.dto.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionMissileAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionMovementDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionMovingManeuverDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionSpellAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionSpellCastDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionStaticManeuverDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMovement;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class TacticalControllerImpl implements TacticalSessionController {

	@Autowired
	private TacticalService tacticalService;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Override
	public Mono<TacticalSession> createTacticalSession(TacticalSessionCreation request) {
		return tacticalService.createSession(request);
	}

	@Override
	public Flux<TacticalSession> findAllTacticalSessions() {
		return tacticalSessionRepository.findAll();
	}

	@Override
	public Mono<TacticalSession> findTacticalSessionsById(String id) {
		return tacticalSessionRepository.findById(id);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return tacticalSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session not found.")))
			.flatMap(tacticalSessionRepository::delete);
	}

	@Override
	public Mono<TacticalRound> getDeclaredAction(String sessionId, String source, TacticalActionPhase phase) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<TacticalRound> removeDeclaredAction(String sessionId, String source, TacticalActionPhase phase) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<TacticalRound> delareMovementAction(String id, TacticalActionMovementDeclaration request) {
		TacticalActionMovement movement = TacticalActionMovement.builder()
			.source(request.getSource())
			.actionPercent(request.getActionPercent())
			.priority(request.getPriority())
			.pace(request.getPace())
			.build();
		log.debug("Declaring movement action for {}", request.getSource());
		return tacticalService.declare(id, movement);
	}

	@Override
	public Mono<TacticalRound> delareMeleeAttack(String id, @Valid TacticalActionMeleeAttackDeclaration action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<TacticalRound> delareMissileAttack(String id, @Valid TacticalActionMissileAttackDeclaration action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<TacticalRound> delareSpellAttack(String id, @Valid TacticalActionSpellAttackDeclaration action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<TacticalRound> delareSpellCast(String id, @Valid TacticalActionSpellCastDeclaration action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<TacticalRound> delareStaticManeuver(String id, @Valid TacticalActionStaticManeuverDeclaration action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<TacticalRound> delareMovingManeuver(String id, @Valid TacticalActionMovingManeuverDeclaration action) {
		// TODO Auto-generated method stub
		return null;
	}

}
