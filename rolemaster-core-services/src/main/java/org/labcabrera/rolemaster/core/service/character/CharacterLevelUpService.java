package org.labcabrera.rolemaster.core.service.character;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;

import reactor.core.publisher.Mono;

public interface CharacterLevelUpService {

	Mono<CharacterInfo> levelUp(String characterId);

}