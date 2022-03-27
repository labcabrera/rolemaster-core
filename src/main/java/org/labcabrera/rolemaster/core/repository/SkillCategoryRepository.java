package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.skill.SkillCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SkillCategoryRepository extends ReactiveMongoRepository<SkillCategory, String> {

}
