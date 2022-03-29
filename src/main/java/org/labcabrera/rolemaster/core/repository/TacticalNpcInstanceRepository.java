package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.tactical.TacticalNpcInstance;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface TacticalNpcInstanceRepository extends ReactiveMongoRepository<TacticalNpcInstance, String> {

	Mono<Long> countByNpcId(String id);

}
