package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.TrainingPackageController;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.TrainingPackage;
import org.labcabrera.rolemaster.core.model.exception.NotFoundException;
import org.labcabrera.rolemaster.core.repository.TrainingPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TrainingPackageControllerImpl implements TrainingPackageController {

	@Autowired
	private TrainingPackageRepository repository;

	@Override
	public Mono<TrainingPackage> findById(String id) {
		return repository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Training package not found.")));
	}

	@Override
	public Flux<TrainingPackage> findAll(RolemasterVersion version, Pageable pageable) {
		TrainingPackage probe = TrainingPackage.builder()
			.version(version)
			.costByProfession(null)
			.build();
		Example<TrainingPackage> example = Example.of(probe);
		return repository.findAll(example, pageable.getSort());
	}

}
