package org.labcabrera.rolemaster.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(name = "Demo")
@RequestMapping("/demo")
public interface DemoController {

	@PostMapping("/initialization")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Demo initialized")
	Mono<Void> initialize();

	@PostMapping("/cleanup")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Clean up data")
	Mono<Void> cleanUp();

}
