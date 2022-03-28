package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.character.TrainingPackage;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Lifestyle tranining packages")
@RequestMapping("/lifestyle-training-packages")
public interface LifestyleTrainingPackagesController {

	@GetMapping("/{id}")
	@Operation(summary = "Lifestyle tranining package by id.")
	Mono<ResponseEntity<TrainingPackage>> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Lifestyle tranining packages search.")
	Flux<TrainingPackage> findAll(
		@ParameterObject @PageableDefault(sort = "id", direction = Direction.ASC, size = 10) Pageable pageable);

}
