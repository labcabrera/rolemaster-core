package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.character.Race;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/races")
public interface RaceController {

	@GetMapping("/{id}")
	Mono<Race> findById(@PathVariable String id);

	@GetMapping
	Flux<Race> findAll();

}
