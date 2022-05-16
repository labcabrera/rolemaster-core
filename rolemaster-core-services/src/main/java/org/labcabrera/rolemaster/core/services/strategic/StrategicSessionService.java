package org.labcabrera.rolemaster.core.services.strategic;

import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.StrategicSessionUpdate;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StrategicSessionService {

	Mono<StrategicSession> findById(JwtAuthenticationToken auth, String id);

	Flux<StrategicSession> findAll(JwtAuthenticationToken auth, Pageable pageable);

	Mono<StrategicSession> createSession(JwtAuthenticationToken auth, StrategicSessionCreation request);

	Mono<TacticalCharacter> addCharacter(String sessionId, String characterId);

	Mono<Void> deleteById(JwtAuthenticationToken auth, String id);

	Mono<StrategicSession> updateSession(String id, StrategicSessionUpdate request);

}