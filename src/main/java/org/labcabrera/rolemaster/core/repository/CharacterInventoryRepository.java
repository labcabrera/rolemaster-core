package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.inventory.CharacterInventory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface CharacterInventoryRepository extends ReactiveMongoRepository<CharacterInventory, String> {

	Mono<CharacterInventory> findByCharacterId(String characterId);

	Mono<Void> deleteByCharacterId(String characterId);

}
