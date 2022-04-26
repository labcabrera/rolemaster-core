package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.CharacterCustomization;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CharacterCustomizationRepository extends ReactiveMongoRepository<CharacterCustomization, String> {

}
