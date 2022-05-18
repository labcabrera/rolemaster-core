package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.CharacterCustomizationController;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.CharacterCustomization;
import org.labcabrera.rolemaster.core.model.character.CharacterCustomizationType;
import org.labcabrera.rolemaster.core.model.exception.NotFoundException;
import org.labcabrera.rolemaster.core.repository.CharacterCustomizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CharacterCustomizationControllerImpl implements CharacterCustomizationController {

	@Autowired
	private CharacterCustomizationRepository repository;

	@Override
	public Mono<CharacterCustomization> getItem(String customizationId) {
		return repository.findById(customizationId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Character customization not found.")));
	}

	@Override
	public Flux<CharacterCustomization> getItem(RolemasterVersion version, CharacterCustomizationType type, Pageable pageable) {
		CharacterCustomization probe = CharacterCustomization.builder()
			.version(version)
			.type(type)
			.build();
		Example<CharacterCustomization> example = Example.of(probe);
		return repository.findAll(example, pageable.getSort());
	}

}
