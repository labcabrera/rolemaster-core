package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.CharacterStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CharacterStatusRepository extends ReactiveMongoRepository<CharacterStatus, String> {

}
