package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface SkillRepository extends ReactiveMongoRepository<Skill, String> {

	@Query("{ 'loadOnNewCharacters': true }")
	Flux<Skill> findSkillsOnNewCharacter();

}
