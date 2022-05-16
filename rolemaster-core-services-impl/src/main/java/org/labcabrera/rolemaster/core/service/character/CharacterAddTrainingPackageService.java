package org.labcabrera.rolemaster.core.service.character;

import org.labcabrera.rolemaster.core.dto.TrainingPackageUpgrade;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Mono;

public interface CharacterAddTrainingPackageService {

	Mono<CharacterInfo> upgrade(JwtAuthenticationToken auth, String characterId, TrainingPackageUpgrade request);

}