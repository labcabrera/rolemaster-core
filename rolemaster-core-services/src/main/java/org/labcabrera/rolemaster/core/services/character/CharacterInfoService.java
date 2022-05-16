package org.labcabrera.rolemaster.core.services.character;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CharacterInfoService {

	Mono<CharacterInfo> insert(@NotNull JwtAuthenticationToken auth, @NotNull CharacterInfo character);

	Mono<CharacterInfo> update(@NotNull JwtAuthenticationToken auth, @NotNull CharacterInfo character);

	Mono<CharacterInfo> findById(@NotNull JwtAuthenticationToken auth, @NotEmpty String id);

	Flux<CharacterInfo> findAll(@NotNull JwtAuthenticationToken auth, Pageable pageable);

	Mono<Void> deleteById(@NotNull JwtAuthenticationToken auth, @NotEmpty String id);

}