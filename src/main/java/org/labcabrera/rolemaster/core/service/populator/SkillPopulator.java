package org.labcabrera.rolemaster.core.service.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
class SkillPopulator extends AbstractJsonPopulator<Skill> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/skills/skills-armor.json",
			"data/populator/skills/skills-artistic.json",
			"data/populator/skills/skills-athletic.json",
			"data/populator/skills/skills-awareness.json",
			"data/populator/skills/skills-body-development.json",
			"data/populator/skills/skills-combat-maneuvers.json",
			"data/populator/skills/skills-communications.json",
			"data/populator/skills/skills-crafts.json",
			"data/populator/skills/skills-influence.json",
			"data/populator/skills/skills-lore.json",
			"data/populator/skills/skills-martial-arts.json",
			"data/populator/skills/skills-outdoor-animal.json",
			"data/populator/skills/skills-outdoor-environmental.json",
			"data/populator/skills/skills-power-point-development.json",
			"data/populator/skills/skills-power-awareness.json",
			"data/populator/skills/skills-science.json",
			"data/populator/skills/skills-self-control.json",
			"data/populator/skills/skills-special-attacks.json",
			"data/populator/skills/skills-special-defenses.json",
			"data/populator/skills/skills-subterfuge.json",
			"data/populator/skills/skills-technical-trade.json",
			"data/populator/skills/skills-urban.json",
			"data/populator/skills/skills-weapons.json");
	}

	@Override
	protected TypeReference<List<Skill>> getTypeReference() {
		return new TypeReference<List<Skill>>() {
		};
	}
}
