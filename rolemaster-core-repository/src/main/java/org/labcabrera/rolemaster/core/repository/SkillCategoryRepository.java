package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.skill.SkillCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SkillCategoryRepository extends ReactiveMongoRepository<SkillCategory, String> {

	Mono<Void> deleteByVersion(RolemasterVersion version);

	Flux<SkillCategory> findByVersion(RolemasterVersion version);

}
