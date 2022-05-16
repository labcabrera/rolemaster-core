package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.Universe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UniverseRepository extends ReactiveMongoRepository<Universe, String> {

}
