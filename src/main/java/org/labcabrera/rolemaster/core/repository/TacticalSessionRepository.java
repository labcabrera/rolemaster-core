package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TacticalSessionRepository extends ReactiveMongoRepository<TacticalSession, String> {

}
