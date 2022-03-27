package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.combat.Attack;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AttackRepository extends ReactiveMongoRepository<Attack, String> {

}
