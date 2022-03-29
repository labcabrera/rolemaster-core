package org.labcabrera.rolemaster.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@RequestMapping("/demo")
@Tag(name = "Demo", description = "Demonstration operations during development (removed on completion).")
public interface DemoController {

	@PostMapping("/initialization")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Demo initialized")
	@Operation(summary = "Deletes existing data and adds an initial set of entities.")
	Mono<Void> initialize();

	@PostMapping("/cleanup")
	@Operation(summary = "Removes all user data.")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Clean up data")
	Mono<Void> cleanUp();

}
