package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.Realm;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RealmRepository extends ReactiveMongoRepository<Realm, String> {

}
