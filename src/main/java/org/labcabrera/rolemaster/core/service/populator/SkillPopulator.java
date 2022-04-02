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
			"data/populator/skills/skills.json",
			"data/populator/skills/skills-armor.json",
			"data/populator/skills/skills-artistic.json",
			"data/populator/skills/skills-athletic.json",
			"data/populator/skills/skills-awareness.json",
			"data/populator/skills/skills-combat-maneuvers.json",
			"data/populator/skills/skills-influence.json",
			"data/populator/skills/skills-martial-arts.json",
			"data/populator/skills/skills-outdoor-animal.json",
			"data/populator/skills/skills-outdoor-environmental.json",
			"data/populator/skills/skills-science.json",
			"data/populator/skills/skills-subterfuge.json");
	}

	@Override
	protected TypeReference<List<Skill>> getTypeReference() {
		return new TypeReference<List<Skill>>() {
		};
	}
}
