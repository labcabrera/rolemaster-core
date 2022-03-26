package org.labcabrera.rolemaster.core.service.populator;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.SkillCategory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class SkillCategoryPopulator extends AbstractJsonPopulator<SkillCategory> {

	@Override
	protected String getResource() {
		return "data/populator/skill-categories.json";
	}

	@Override
	protected TypeReference<List<SkillCategory>> getTypeReference() {
		return new TypeReference<List<SkillCategory>>() {
		};
	}

}
