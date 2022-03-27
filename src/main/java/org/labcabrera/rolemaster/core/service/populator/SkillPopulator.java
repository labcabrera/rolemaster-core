package org.labcabrera.rolemaster.core.service.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class SkillPopulator extends AbstractJsonPopulator<Skill> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/skills.json",
			"data/populator/skills-artistic.json",
			"data/populator/skills-athletic.json",
			"data/populator/skills-awareness.json",
			"data/populator/skills-combat-maneuvers.json",
			"data/populator/skills-subterfuge.json");
	}

	@Override
	protected TypeReference<List<Skill>> getTypeReference() {
		return new TypeReference<List<Skill>>() {
		};
	}
}
