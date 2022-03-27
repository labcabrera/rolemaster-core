package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.SkillCategoryController;
import org.labcabrera.rolemaster.core.model.skill.SkillCategory;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
	public Flux<SkillCategory> findAll() {
		return repository.findAll();
	}

}
