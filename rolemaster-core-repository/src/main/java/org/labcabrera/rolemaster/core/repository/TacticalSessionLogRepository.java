package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.tactical.TacticalSessionLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface TacticalSessionLogRepository extends ReactiveMongoRepository<TacticalSessionLog, String> {

	Mono<Void> deleteByTacticalSessionId(String tacticalSessionId);

}
