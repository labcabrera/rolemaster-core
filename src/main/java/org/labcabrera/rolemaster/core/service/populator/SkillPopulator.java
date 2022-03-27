package org.labcabrera.rolemaster.core.service.populator;

import java.util.List;

import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class SkillPopulator extends AbstractJsonPopulator<Skill> {

	@Override
	protected String getResource() {
		return "data/populator/skills.json";
	}

	@Override
	protected TypeReference<List<Skill>> getTypeReference() {
		return new TypeReference<List<Skill>>() {
		};
	}
}
