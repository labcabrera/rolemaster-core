package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.LifestyleTrainingPackagesController;
import org.labcabrera.rolemaster.core.model.character.TrainingPackage;
import org.labcabrera.rolemaster.core.repository.LifestyleTrainingPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class LifestyleTrainingPackagesControllerImpl implements LifestyleTrainingPackagesController {

	@Autowired
	private LifestyleTrainingPackageRepository repository;

	@Override
	public Mono<ResponseEntity<TrainingPackage>> findById(String id) {
		return repository.findById(id)
			.map(ResponseEntity::ok)
			.defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
	}

	@Override
	public Flux<TrainingPackage> findAll(Pageable pageable) {
		return repository.findAll();
	}

}
