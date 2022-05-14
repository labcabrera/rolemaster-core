package org.labcabrera.rolemaster.core.service.character.creation;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreation;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;

@Validated
public interface CharacterCreationService {

	Mono<CharacterInfo> create(JwtAuthenticationToken auth, @Valid CharacterCreation request);

}