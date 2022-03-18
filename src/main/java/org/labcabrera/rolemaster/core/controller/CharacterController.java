package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.impl.CharacterCreationRequestImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import reactor.core.publisher.Mono;

@RequestMapping("/characters")
public interface CharacterController {

	@GetMapping("/{id}")
	Mono<CharacterInfo> findById(@PathVariable String id);

	@PostMapping
	Mono<CharacterInfo> create(@RequestBody CharacterCreationRequestImpl request);
}
