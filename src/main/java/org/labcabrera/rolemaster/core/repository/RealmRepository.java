package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.Realm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface RealmRepository extends ReactiveMongoRepository<Realm, String> {

	Flux<Realm> findAllByIdNotNullOrderByNameAsc(final Pageable page);

}
