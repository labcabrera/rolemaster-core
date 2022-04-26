package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface TacticalSessionRepository extends ReactiveMongoRepository<TacticalSession, String> {

	Flux<TacticalSession> findByStrategicSessionId(String strategicSessionId);

}
