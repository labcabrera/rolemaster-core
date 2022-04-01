package org.labcabrera.rolemaster.core.controller;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.dto.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionMissileAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionMovementDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionMovingManeuverDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionSpellAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionSpellCastDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalActionStaticManeuverDeclaration;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/tactical-sessions")
@Tag(name = "Tactical sessions", description = "Operations on the tactical environment.")
public interface TacticalSessionController {

	@GetMapping
	@Operation(summary = "Find all tactical sessions.")
	Flux<TacticalSession> findAllTacticalSessions();

	@GetMapping("/{id}")
	@Operation(summary = "Find tactical session by id.")
	Mono<TacticalSession> findTacticalSessionsById(@PathVariable String id);

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Tactical session created.")
	@Operation(summary = "Create a new tactical session.")
	Mono<TacticalSession> createTacticalSession(@RequestBody TacticalSessionCreation request);

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Tactical session deleted.")
	@Operation(summary = "Delete tactical session by id.")
	Mono<Void> deleteById(@PathVariable String id);

	@GetMapping("/{id}/actions/declarations/{source}/{phase}")
	@Operation(summary = "Get a declared action from a given character and phase.")
	Mono<TacticalRound> getDeclaredAction(
		@Parameter(description = "Tactical session identifier.", required = true) @PathVariable("id") String sessionId,
		@Parameter(description = "Character performing the action.", required = true) @PathVariable String source,
		@Parameter(description = "Tactical phase.", required = true) @PathVariable TacticalActionPhase phase);

	@DeleteMapping("/{id}/actions/declarations/{source}/{phase}")
	@Operation(summary = "Eliminates a previously declared action.")
	Mono<TacticalRound> removeDeclaredAction(
		@Parameter(description = "Tactical session identifier.", required = true) @PathVariable("id") String sessionId,
		@Parameter(description = "Character performing the action.", required = true) @PathVariable String source,
		@Parameter(description = "Tactical phase.", required = true) @PathVariable TacticalActionPhase phase);

	@PostMapping("/{id}/actions/declarations/movements")
	@Operation(summary = "Declares a movement.")
	Mono<TacticalRound> delareMovementAction(@PathVariable String id, @Valid TacticalActionMovementDeclaration action);

	//TODO create dtos

	@PostMapping("/{id}/actions/declarations/melee-attacks")
	@Operation(summary = "Declares a melee attack.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Melee attack declared.")
	Mono<TacticalRound> delareMeleeAttack(@PathVariable String id, @Valid TacticalActionMeleeAttackDeclaration action);

	@PostMapping("/{id}/actions/declarations/missile-attacks")
	@Operation(summary = "Declares a missile attack.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Missile attack declared.")
	Mono<TacticalRound> delareMissileAttack(@PathVariable String id, @Valid TacticalActionMissileAttackDeclaration action);

	@PostMapping("/{id}/actions/declarations/spell-attacks")
	@Operation(summary = "Declares a spell attack.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Spell attack declared.")
	Mono<TacticalRound> delareSpellAttack(@PathVariable String id, @Valid TacticalActionSpellAttackDeclaration action);

	@PostMapping("/{id}/actions/declarations/spell-casts")
	@Operation(summary = "Declares a spell cast.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Spell cast declared.")
	Mono<TacticalRound> delareSpellCast(@PathVariable String id, @Valid TacticalActionSpellCastDeclaration action);

	@PostMapping("/{id}/actions/declarations/static-maneuvers")
	@Operation(summary = "Declares a spell cast.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Static maneuver declared.")
	Mono<TacticalRound> delareStaticManeuver(@PathVariable String id, @Valid TacticalActionStaticManeuverDeclaration action);

	@PostMapping("/{id}/actions/declarations/moving-maneuvers")
	@Operation(summary = "Declares a moving maneuver.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Moving maneuver declared.")
	Mono<TacticalRound> delareMovingManeuver(@PathVariable String id, @Valid TacticalActionMovingManeuverDeclaration action);
}
