package org.labcabrera.rolemaster.core.services.rmss.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.skill.SkillCategory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
class SkillCategoryPopulator extends AbstractJsonPopulator<SkillCategory> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList("data/populator/skill-categories.json");
	}

	@Override
	protected TypeReference<List<SkillCategory>> getTypeReference() {
		return new TypeReference<List<SkillCategory>>() {
		};
	}

}
