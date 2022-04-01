package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.SkillController;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.util.StringUtils;
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
	public Flux<Skill> find(String categoryId, Pageable pageable) {
		Example<Skill> example = Example.of(new Skill());
		example.getProbe().setLoadOnNewCharacters(null);
		example.getProbe().setAttributeBonus(null);
		example.getProbe().setModifiers(null);
		example.getProbe().setProgressionType(null);
		example.getProbe().setSkillBonus(null);
		example.getProbe().setType(null);
		if (StringUtils.isNotBlank(categoryId)) {
			example.getProbe().setCategoryId(categoryId);
		}
		return repository.findAll(example, pageable.getSort());
	}

}
