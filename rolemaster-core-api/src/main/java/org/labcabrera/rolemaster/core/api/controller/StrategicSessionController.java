package org.labcabrera.rolemaster.core.api.controller;

import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.StrategicSessionUpdate;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/strategic-sessions")
@Tag(name = "Strategic sessions", description = "Operations on the strategic environment.")
public interface StrategicSessionController {

	@GetMapping("/{id}")
	@Operation(summary = "Get session.")
	@ApiResponse(responseCode = "200", description = "Success")
	@ApiResponse(responseCode = "404", description = "Not found")
	Mono<StrategicSession> findById(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@PathVariable String id);

	@GetMapping
	@Operation(summary = "Get all sessions.")
	@ResponseStatus(code = HttpStatus.OK, reason = "Success")
	Flux<StrategicSession> findAll(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 10) Pageable pageable);

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Session created.")
	@Operation(summary = "Session creation.")
	Mono<StrategicSession> createSession(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@RequestBody StrategicSessionCreation sessionCreationRequest);

	@PatchMapping("/{id}")
	@Operation(summary = "Session update.")
	@ApiResponse(responseCode = "200", description = "Updated")
	@ApiResponse(responseCode = "404", description = "Not found")
	Mono<StrategicSession> updateSession(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@PathVariable String id,
		@RequestBody StrategicSessionUpdate sessionUpdateRequest);

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Deleted session")
	@ApiResponse(responseCode = "204", description = "Session deleted")
	@ApiResponse(responseCode = "404", description = "Not found")
	@Operation(summary = "Delete session.")
	Mono<Void> deleteById(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@PathVariable String id);

}
