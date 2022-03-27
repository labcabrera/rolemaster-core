package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.SkillController;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SkillControllerImpl implements SkillController {

	@Autowired
	private SkillRepository repository;

	@Override
	public Mono<Skill> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Flux<Skill> findAll() {
		return repository.findAll();
	}

}
