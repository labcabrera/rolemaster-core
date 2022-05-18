package org.labcabrera.rolemaster.core.services.tactical;

import java.util.List;
import java.util.Set;

import org.labcabrera.rolemaster.core.dto.tactical.TacticalCharacterModification;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TacticalCharacterService {

	Mono<TacticalCharacter> findById(String id);

	Flux<TacticalCharacter> findAll(Pageable pageable);

	Mono<TacticalCharacter> create(String tacticalSessionId, String characterId);

	Mono<TacticalCharacter> update(JwtAuthenticationToken auth, String tacticalCharacterId, TacticalCharacterModification modification);

	Mono<Void> delete(String id);

	Mono<List<TacticalCharacter>> getStatusAsList(Set<String> characterIdentifiers);

}