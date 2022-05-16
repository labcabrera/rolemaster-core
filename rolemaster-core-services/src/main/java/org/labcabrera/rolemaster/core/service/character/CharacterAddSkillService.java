package org.labcabrera.rolemaster.core.service.character;

import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Mono;

public interface CharacterAddSkillService {

	Mono<CharacterInfo> addSkill(JwtAuthenticationToken auth, String characterId, AddSkill request);

	Mono<CharacterInfo> addSkill(CharacterInfo characterInfo, AddSkill request);

}