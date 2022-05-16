package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.item.Weapon;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WeaponRepository extends ReactiveMongoRepository<Weapon, String> {

}
