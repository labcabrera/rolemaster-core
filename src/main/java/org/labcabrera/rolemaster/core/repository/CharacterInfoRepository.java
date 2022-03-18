package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CharacterInfoRepository extends ReactiveMongoRepository<CharacterInfo, String> {

}
