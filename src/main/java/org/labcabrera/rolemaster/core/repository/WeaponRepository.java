package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.item.WeaponCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WeaponRepository extends ReactiveMongoRepository<WeaponCategory, String> {

}
