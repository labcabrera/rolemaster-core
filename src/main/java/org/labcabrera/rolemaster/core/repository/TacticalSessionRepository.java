package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.session.TacticalSession;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TacticalSessionRepository extends ReactiveMongoRepository<TacticalSession, String> {

}
