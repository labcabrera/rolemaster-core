package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.SkillController;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.labcabrera.rolemaster.core.service.Messages.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import io.micrometer.core.instrument.util.StringUtils;
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
	public Flux<Skill> find(String categoryId, Pageable pageable) {
		Example<Skill> example = Example.of(new Skill());
		example.getProbe().setLoadOnNewCharacters(null);
		example.getProbe().setAttributeBonus(null);
		example.getProbe().setModifiers(null);
		example.getProbe().setProgressionType(null);
		example.getProbe().setSkillBonus(null);
		example.getProbe().setType(null);
		example.getProbe().setCustomizableOptions(null);
		example.getProbe().setCustomizationRestriction(null);
		if (StringUtils.isNotBlank(categoryId)) {
			example.getProbe().setCategoryId(categoryId);
		}
		return repository.findAll(example, pageable.getSort());
	}

}
