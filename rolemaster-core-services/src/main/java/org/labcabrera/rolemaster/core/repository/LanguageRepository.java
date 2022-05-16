package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.Language;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LanguageRepository extends ReactiveMongoRepository<Language, String> {

}
