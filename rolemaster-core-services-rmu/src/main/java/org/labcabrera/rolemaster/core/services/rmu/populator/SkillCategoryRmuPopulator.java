package org.labcabrera.rolemaster.core.services.rmu.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.skill.SkillCategory;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.labcabrera.rolemaster.core.services.commons.populator.AbstractJsonPopulator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import reactor.core.publisher.Mono;

@Component
class SkillCategoryRmuPopulator extends AbstractJsonPopulator<SkillCategory> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList("data/populator/rmu/skill-categories-rmu.json");
	}

	@Override
	protected Mono<Void> delete() {
		return ((SkillCategoryRepository) repository).deleteByVersion(RolemasterVersion.RMU);
	}

	@Override
	protected TypeReference<List<SkillCategory>> getTypeReference() {
		return new TypeReference<List<SkillCategory>>() {
		};
	}

}
