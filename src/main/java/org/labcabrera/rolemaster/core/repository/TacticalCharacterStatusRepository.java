package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TacticalCharacterStatusRepository extends ReactiveMongoRepository<TacticalCharacterContext, String> {

}
