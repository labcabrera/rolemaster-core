package org.labcabrera.rolemaster.core.repository;

import java.util.Set;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TacticalCharacterRepository extends ReactiveMongoRepository<TacticalCharacter, String> {

	Flux<TacticalCharacter> findByCharacterId(Set<String> characterIdentifiers);

	Flux<TacticalCharacter> findByTacticalSessionId(String tacticalSessionId);

	Mono<Void> deleteByTacticalSessionId(String tacticalSessionId);

}
