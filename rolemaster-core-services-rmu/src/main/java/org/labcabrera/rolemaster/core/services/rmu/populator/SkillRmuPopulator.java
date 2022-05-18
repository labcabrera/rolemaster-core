package org.labcabrera.rolemaster.core.services.rmu.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.labcabrera.rolemaster.core.services.commons.populator.AbstractJsonPopulator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import reactor.core.publisher.Mono;

@Component
class SkillRmuPopulator extends AbstractJsonPopulator<Skill> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList("data/populator/rmu/skills.json");
	}

	@Override
	protected List<Skill> collectValues() {
		List<Skill> list = super.collectValues();
		list.stream().forEach(e -> e.setVersion(RolemasterVersion.RMU));
		return list;
	}

	@Override
	protected Mono<Void> delete() {
		return ((SkillRepository) repository).deleteByVersion(RolemasterVersion.RMU);
	}

	@Override
	protected TypeReference<List<Skill>> getTypeReference() {
		return new TypeReference<List<Skill>>() {
		};
	}
}
