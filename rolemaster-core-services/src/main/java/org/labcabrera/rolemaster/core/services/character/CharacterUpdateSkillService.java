package org.labcabrera.rolemaster.core.services.character;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.dto.SkillUpgrade;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Mono;

public interface CharacterUpdateSkillService {

	Mono<CharacterInfo> updateRanks(
		@NotNull JwtAuthenticationToken auth,
		@NotEmpty String characterId,
		@NotNull @Valid SkillUpgrade request);

}