package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.character.Skill;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Skills")
@RequestMapping("/skills")
public interface SkillController {

	@GetMapping("/{id}")
	@Operation(summary = "Skill search by id.")
	Mono<Skill> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Skill search.")
	Flux<Skill> findAll();

}
