package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.Profession;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProfessionRepository extends ReactiveMongoRepository<Profession, String> {

}
