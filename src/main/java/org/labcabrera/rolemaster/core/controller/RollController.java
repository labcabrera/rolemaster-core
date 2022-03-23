package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(name = "Rolls", description = "Provides utilities to simulate dice rolls.")
@RequestMapping("/rolls")
public interface RollController {

	@GetMapping("/d/{max}")
	Mono<Integer> randomRoll(@PathVariable Integer max);

	@GetMapping("/d/100/open")
	Mono<OpenRoll> randomOpenRoll();

}
