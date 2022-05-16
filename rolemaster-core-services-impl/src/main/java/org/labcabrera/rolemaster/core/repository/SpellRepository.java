package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.spell.Spell;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpellRepository extends ReactiveMongoRepository<Spell, String> {

}
