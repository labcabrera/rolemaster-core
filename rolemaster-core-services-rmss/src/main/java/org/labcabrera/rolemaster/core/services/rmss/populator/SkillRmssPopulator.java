package org.labcabrera.rolemaster.core.services.rmss.populator;

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
class SkillRmssPopulator extends AbstractJsonPopulator<Skill> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/rmss/skills/skills-armor.json",
			"data/populator/rmss/skills/skills-artistic.json",
			"data/populator/rmss/skills/skills-athletic.json",
			"data/populator/rmss/skills/skills-awareness.json",
			"data/populator/rmss/skills/skills-body-development.json",
			"data/populator/rmss/skills/skills-combat-maneuvers.json",
			"data/populator/rmss/skills/skills-communications.json",
			"data/populator/rmss/skills/skills-crafts.json",
			"data/populator/rmss/skills/skills-influence.json",
			"data/populator/rmss/skills/skills-lore.json",
			"data/populator/rmss/skills/skills-martial-arts.json",
			"data/populator/rmss/skills/skills-outdoor-animal.json",
			"data/populator/rmss/skills/skills-outdoor-environmental.json",
			"data/populator/rmss/skills/skills-power-point-development.json",
			"data/populator/rmss/skills/skills-power-awareness.json",
			"data/populator/rmss/skills/skills-science.json",
			"data/populator/rmss/skills/skills-self-control.json",
			"data/populator/rmss/skills/skills-special-attacks.json",
			"data/populator/rmss/skills/skills-special-defenses.json",
			"data/populator/rmss/skills/skills-subterfuge.json",
			"data/populator/rmss/skills/skills-technical-trade.json",
			"data/populator/rmss/skills/skills-urban.json",
			"data/populator/rmss/skills/skills-weapons.json");
	}

	@Override
	protected List<Skill> collectValues() {
		List<Skill> list = super.collectValues();
		list.stream().forEach(e -> e.setVersion(RolemasterVersion.RMSS));
		return list;
	}

	@Override
	protected Mono<Void> delete() {
		return ((SkillRepository) repository).deleteByVersion(RolemasterVersion.RMSS);
	}

	@Override
	protected TypeReference<List<Skill>> getTypeReference() {
		return new TypeReference<List<Skill>>() {
		};
	}
}
