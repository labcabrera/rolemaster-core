package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.inventory.CharacterInventory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CharacterInventoryRepository extends ReactiveMongoRepository<CharacterInventory, String> {

}
