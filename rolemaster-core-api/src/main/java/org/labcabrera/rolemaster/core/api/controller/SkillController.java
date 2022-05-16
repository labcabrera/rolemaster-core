package org.labcabrera.rolemaster.core.api.controller;

import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/skills")
@Tag(name = "Skills", description = "List of character skills.")
public interface SkillController {

	@GetMapping("/{id}")
	@Operation(summary = "Skill search by id.")
	Mono<Skill> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Skill search.")
	Flux<Skill> find(
		@Parameter(name = "categoryId", required = false) @RequestParam(required = false) String categoryId,
		@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 10) Pageable pageable);

}
