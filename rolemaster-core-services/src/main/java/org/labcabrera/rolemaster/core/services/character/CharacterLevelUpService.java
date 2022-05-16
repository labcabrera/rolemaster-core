package org.labcabrera.rolemaster.core.services.character;

import javax.validation.constraints.NotEmpty;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;

import reactor.core.publisher.Mono;

public interface CharacterLevelUpService {

	Mono<CharacterInfo> levelUp(@NotEmpty String characterId);

}