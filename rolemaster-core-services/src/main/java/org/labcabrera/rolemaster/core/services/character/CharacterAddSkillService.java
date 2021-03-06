package org.labcabrera.rolemaster.core.services.character;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Mono;

public interface CharacterAddSkillService {

	Mono<CharacterInfo> addSkill(@NotNull JwtAuthenticationToken auth, @NotEmpty String characterId, @NotNull @Valid AddSkill request);

	Mono<CharacterInfo> addSkill(@NotNull CharacterInfo characterInfo, @NotEmpty @Valid AddSkill request);

}