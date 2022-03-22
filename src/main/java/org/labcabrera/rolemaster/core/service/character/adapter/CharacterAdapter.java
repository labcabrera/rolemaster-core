package org.labcabrera.rolemaster.core.service.character.adapter;

import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;

import reactor.core.publisher.Mono;

public interface CharacterAdapter {

	Mono<CharacterModificationContext> apply(CharacterModificationContext context);

}
