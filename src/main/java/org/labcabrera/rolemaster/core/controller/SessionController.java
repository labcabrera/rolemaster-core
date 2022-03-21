package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.character.Session;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Sessions")
@RequestMapping("/sessions")
public interface SessionController {

	@GetMapping("/{id}")
	@Operation(description = "Get session.")
	Mono<Session> findById(@PathVariable String id);

	@GetMapping
	@Operation(description = "Get all sessions.")
	Flux<Session> findAll();

	@PostMapping
	@Operation(description = "Create new session.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Created")
	Mono<Session> createSession(String name);

	@DeleteMapping("/{id}")
	@Operation(description = "Delete session.")
	Mono<Void> deleteById(@PathVariable String id);

	@DeleteMapping
	@Operation(description = "Delete all sessions.")
	Mono<Void> deleteAll();

}
