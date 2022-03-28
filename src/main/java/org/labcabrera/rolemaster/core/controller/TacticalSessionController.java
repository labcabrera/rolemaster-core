package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.dto.TacticalSessionCreationRequest;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Tactical Sessions")
@RequestMapping("/tactical/sessions")
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
	Mono<TacticalSession> createTacticalSession(@RequestBody TacticalSessionCreationRequest request);

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Tactical session deleted.")
	@Operation(summary = "Delete tactical session by id.")
	Mono<Void> deleteById(@PathVariable String id);
}
