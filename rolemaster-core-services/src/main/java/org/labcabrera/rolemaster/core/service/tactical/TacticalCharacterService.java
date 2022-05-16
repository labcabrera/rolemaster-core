package org.labcabrera.rolemaster.core.service.tactical;

import java.util.List;
import java.util.Set;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TacticalCharacterService {

	Mono<TacticalCharacter> findById(String id);

	Flux<TacticalCharacter> findAll(Pageable pageable);

	Mono<TacticalCharacter> create(String tacticalSessionId, String characterId);

	Mono<Void> delete(String id);

	Mono<List<TacticalCharacter>> getStatusAsList(Set<String> characterIdentifiers);

	Mono<Void> deleteAll();

}