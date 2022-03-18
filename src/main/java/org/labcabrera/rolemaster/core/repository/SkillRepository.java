package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.character.Skill;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SkillRepository extends ReactiveMongoRepository<Skill, String> {

}
