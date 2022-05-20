package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface RaceRepository extends ReactiveMongoRepository<Race, String> {

	Mono<Void> deleteByVersion(RolemasterVersion version);

}
