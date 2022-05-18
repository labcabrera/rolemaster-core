package org.labcabrera.rolemaster.core.services.character.creation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.dto.character.CharacterCreation;
import org.labcabrera.rolemaster.core.model.RolemasterVersionService;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;

@Validated
public interface CharacterCreationService extends RolemasterVersionService {

	Mono<CharacterInfo> create(@NotNull JwtAuthenticationToken auth, @Valid @NotNull CharacterCreation request);

}