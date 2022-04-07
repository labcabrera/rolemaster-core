package org.labcabrera.rolemaster.core.controller;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.dto.actions.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
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
import reactor.core.publisher.Mono;

@RequestMapping("/tactical-sessions")
@Tag(name = "Tactical session actions", description = "Tactical session actions.")
public interface TacticalSessionActionController {

	@GetMapping("/{id}/actions/{source}/{priority}")
	@Operation(summary = "Get a declared action from a given character and phase.")
	Mono<TacticalRound> getDeclaredAction(
		@Parameter(description = "Tactical session identifier.", required = true) @PathVariable("id") String sessionId,
		@Parameter(description = "Character performing the action.", required = true) @PathVariable("source") String source,
		@Parameter(description = "Tactical phase.", required = true) @PathVariable("priority") String phase);

	@PostMapping("/{id}/actions")
	@Operation(summary = "Declares a tactical action.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Action declared.")
	Mono<TacticalRound> delare(
		@Parameter(description = "Tactical session identifier.", required = true) @PathVariable("id") String id,
		@Valid @RequestBody TacticalActionDeclaration action);

	@DeleteMapping("/{id}/actions/{source}/{priority}")
	@Operation(summary = "Eliminates a previously declared action.")
	Mono<TacticalRound> removeDeclaredAction(
		@Parameter(description = "Tactical session identifier.", required = true) @PathVariable("id") String sessionId,
		@Parameter(description = "Character performing the action.", required = true) @PathVariable("source") String source,
		@Parameter(description = "Tactical phase.", required = true) @PathVariable("priority") String priority);

}
