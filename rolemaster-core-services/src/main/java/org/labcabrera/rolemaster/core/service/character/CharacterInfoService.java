package org.labcabrera.rolemaster.core.service.character;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CharacterInfoService {

	Mono<CharacterInfo> insert(JwtAuthenticationToken auth, CharacterInfo character);

	Mono<CharacterInfo> update(JwtAuthenticationToken auth, CharacterInfo character);

	Mono<CharacterInfo> findById(JwtAuthenticationToken auth, String id);

	Flux<CharacterInfo> findAll(JwtAuthenticationToken auth, Pageable pageable);

	Mono<Void> deleteById(JwtAuthenticationToken auth, String id);

}