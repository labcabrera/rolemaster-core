package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.TacticalSessionActionController;
import org.labcabrera.rolemaster.core.dto.actions.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.actions.TacticalActionMissileAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.actions.TacticalActionMovementDeclaration;
import org.labcabrera.rolemaster.core.dto.actions.TacticalActionMovingManeuverDeclaration;
import org.labcabrera.rolemaster.core.dto.actions.TacticalActionSpellAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.actions.TacticalActionSpellCastDeclaration;
import org.labcabrera.rolemaster.core.dto.actions.TacticalActionStaticManeuverDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMovement;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class TacticalSessionActionControllerImpl implements TacticalSessionActionController {

	@Autowired
	private TacticalService tacticalService;

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
