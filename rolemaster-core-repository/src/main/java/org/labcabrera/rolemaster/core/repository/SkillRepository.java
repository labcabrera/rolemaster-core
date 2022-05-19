package org.labcabrera.rolemaster.core.repository;

import java.util.List;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SkillRepository extends ReactiveMongoRepository<Skill, String> {

	@Query("{ 'loadOnNewCharacters': true }")
	Flux<Skill> findSkillsOnNewCharacter();

	@Query("{ 'id': { $in: ?0 } }")
	Flux<Skill> findByIds(List<String> ids);

	@Query("{$or :[{'id': { $in: ?0 } },{'loadOnNewCharacters': true}]}")
	Flux<Skill> findByIdsOnNewCharacter(List<String> ids);

	Flux<Skill> findByVersion(RolemasterVersion version);

	Mono<Void> deleteByVersion(RolemasterVersion version);

}
