package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.SkillController;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.labcabrera.rolemaster.core.services.commons.Messages.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SkillControllerImpl implements SkillController {

	@Autowired
	private SkillRepository repository;

	@Override
	public Mono<Skill> findById(String id) {
		return repository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.missingSkill(id))));
	}

	@Override
	public Flux<Skill> find(RolemasterVersion version, String categoryId, Pageable pageable) {
		Skill probe = Skill.builder()
			.customizableOptions(null)
			.loadOnNewCharacters(null)
			.attributeBonus(null)
			.progressionType(null)
			.skillBonus(null)
			.modifiers(null)
			.version(version)
			.categoryId(categoryId)
			.build();
		Example<Skill> example = Example.of(probe);
		return repository.findAll(example, pageable.getSort());
	}

}
