package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.LifestyleTrainingPackage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LifestyleTrainingPackageRepository extends ReactiveMongoRepository<LifestyleTrainingPackage, String> {

}
