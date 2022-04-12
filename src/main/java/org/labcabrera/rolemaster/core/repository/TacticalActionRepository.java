package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TacticalActionRepository extends ReactiveMongoRepository<TacticalAction, String> {

	Mono<Boolean> existsByRoundIdAndSourceAndPriority(String roundId, String source, TacticalActionPhase priority);

	Flux<TacticalAction> findByRoundId(String roundId);

	Flux<TacticalAction> findByRoundIdOrderByEffectiveInitiativeDesc(String roundId);

}
