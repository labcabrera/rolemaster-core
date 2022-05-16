package org.labcabrera.rolemaster.core.api.controller;

import org.labcabrera.rolemaster.core.model.character.Language;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/languages")
@Tag(name = "Languages", description = "List of languages.")
public interface LanguageController {

	@GetMapping("/{id}")
	@Operation(summary = "Language search by id.")
	Mono<Language> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Language search.")
	Flux<Language> find(
		@RequestParam(name = "universeId", required = false) String universeId,
		@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 10) Pageable pageable);

}
