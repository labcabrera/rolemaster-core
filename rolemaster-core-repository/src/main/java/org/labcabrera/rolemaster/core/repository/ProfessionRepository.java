package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface ProfessionRepository extends ReactiveMongoRepository<Profession, String> {

	Mono<Void> deleteByVersion(RolemasterVersion version);

}
