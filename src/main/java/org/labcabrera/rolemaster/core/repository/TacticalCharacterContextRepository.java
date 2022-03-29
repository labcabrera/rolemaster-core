package org.labcabrera.rolemaster.core.repository;

import java.util.Set;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface TacticalCharacterContextRepository extends ReactiveMongoRepository<TacticalCharacterContext, String> {

	Flux<TacticalCharacterContext> findByCharacterId(Set<String> characterIdentifiers);

}
