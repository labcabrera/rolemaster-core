package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.tactical.CharacterTacticalContext;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CharacterStatusRepository extends ReactiveMongoRepository<CharacterTacticalContext, String> {

}
