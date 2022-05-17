package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.SkillCategoryController;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.skill.SkillCategory;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SkillCategoryControllerImpl implements SkillCategoryController {

	@Autowired
	private SkillCategoryRepository repository;

	@Override
	public Mono<SkillCategory> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Flux<SkillCategory> findAll(RolemasterVersion version, Pageable pageable) {
		SkillCategory probe = SkillCategory.builder()
			.attributeBonus(null)
			.useRealmAttributeBonus(null)
			.modifiers(null)
			.progressionType(null)
			.version(version)
			.build();
		Example<SkillCategory> example = Example.of(probe);
		return repository.findAll(example, pageable.getSort());
	}

}
