package org.labcabrera.rolemaster.core.controller;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.FumbleExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.WeaponBreakageExecution;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
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

@RequestMapping("/tactical-actions")
@Tag(name = "Tactical session actions", description = "Tactical session actions.")
public interface TacticalSessionActionController {

	@GetMapping("/{id}")
	@Operation(summary = "Get a declared action from a given character and phase.")
	Mono<TacticalAction> getDeclaredAction(
		@Parameter(description = "Tactical action identifier.", required = true) @PathVariable("id") String actionId);

	@GetMapping("/rounds/{id}")
	@Operation(summary = "Get all actions from a given round.")
	Flux<TacticalAction> findActionsByRound(
		@Parameter(description = "Round identifier.", required = true) @PathVariable("id") String roundId);

	@PostMapping
	@Operation(summary = "Declares a tactical action.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Action declared.")
	Mono<TacticalAction> declare(
		@Valid @RequestBody TacticalActionDeclaration actionDeclaration);

	@PostMapping("/{id}/execution")
	@Operation(summary = "Executes a tactical action.")
	Mono<TacticalAction> execute(
		@PathVariable("id") String actionId,
		@Valid @RequestBody TacticalActionExecution actionDeclaration);

	@PostMapping("/{id}/execution/breakage")
	@Operation(summary = "Executes a weapon breakage result.")
	Mono<TacticalAction> executeBreakage(
		@PathVariable("id") String actionId,
		@RequestBody WeaponBreakageExecution request);

	@PostMapping("/{id}/execution/critical")
	@Operation(summary = "Executes a critical result.")
	Mono<TacticalAction> executeCritical(
		@PathVariable("id") String actionId,
		@RequestBody AttackCriticalExecution request);

	@PostMapping("/{id}/execution/fumble")
	@Operation(summary = "Executes a critical result.")
	Mono<TacticalAction> executeFumble(
		@PathVariable("id") String actionId,
		@RequestBody FumbleExecution request);

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminates a previously declared action.")
	Mono<Void> removeDeclaredAction(
		@Parameter(description = "Tactical action identifier.", required = true) @PathVariable("id") String actionId);

}
