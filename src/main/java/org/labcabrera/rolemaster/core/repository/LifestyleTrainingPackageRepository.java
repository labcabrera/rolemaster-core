package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.TrainingPackage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LifestyleTrainingPackageRepository extends ReactiveMongoRepository<TrainingPackage, String> {

}
