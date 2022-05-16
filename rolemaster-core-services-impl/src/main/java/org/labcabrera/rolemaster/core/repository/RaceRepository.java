package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.Race;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RaceRepository extends ReactiveMongoRepository<Race, String> {

}
