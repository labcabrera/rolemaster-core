package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.TrainingPackagesController;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.character.TrainingPackage;
import org.labcabrera.rolemaster.core.repository.TrainingPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TrainingPackagesControllerImpl implements TrainingPackagesController {

	@Autowired
	private TrainingPackageRepository repository;

	@Override
	public Mono<TrainingPackage> findById(String id) {
		return repository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Training package not found.")));
	}

	@Override
	public Flux<TrainingPackage> findAll(Pageable pageable) {
		return repository.findAll(pageable.getSort());
	}

}
