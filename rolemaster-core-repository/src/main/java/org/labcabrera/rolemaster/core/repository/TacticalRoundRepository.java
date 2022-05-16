package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface TacticalRoundRepository extends ReactiveMongoRepository<TacticalRound, String> {

	Mono<TacticalRound> findFirstByTacticalSessionIdOrderByRoundDesc(String tacticalSessionId);

	Mono<Void> deleteByTacticalSessionId(String tacticalSessionId);

}
