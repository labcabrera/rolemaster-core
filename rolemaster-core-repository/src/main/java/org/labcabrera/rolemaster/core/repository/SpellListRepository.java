package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.spell.SpellList;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpellListRepository extends ReactiveMongoRepository<SpellList, String> {

}
