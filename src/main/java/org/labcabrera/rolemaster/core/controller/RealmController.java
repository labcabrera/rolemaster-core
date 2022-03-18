package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.Realm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Realms")
@RequestMapping("/realms")
public interface RealmController {

	@GetMapping("/{id}")
	@Operation(summary = "Realm of power search by id.")
	Mono<Realm> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Realm of power search.")
	Flux<Realm> findAll();

}
