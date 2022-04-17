package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface CharacterItemRepository extends ReactiveMongoRepository<CharacterItem, String> {

	Flux<CharacterItem> findByCharacterId(String characterId);

}
