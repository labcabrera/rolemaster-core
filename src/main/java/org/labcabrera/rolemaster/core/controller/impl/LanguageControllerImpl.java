package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.LanguageController;
import org.labcabrera.rolemaster.core.model.character.Language;
import org.labcabrera.rolemaster.core.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class LanguageControllerImpl implements LanguageController {

	@Autowired
	private LanguageRepository repository;

	@Override
	public Mono<Language> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Flux<Language> find(String universeId, Pageable pageable) {
		Language probe = new Language();
		probe.setUniverseId(universeId);
		Example<Language> example = Example.of(probe);
		return repository.findAll(example, pageable.getSort());
	}

}
