package org.labcabrera.rolemaster.core.services.rmss.character.creation;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
class CharacterCreationSkillService {

	@Autowired
	private SkillRepository skillRepository;

	public Flux<Skill> getSkills(Race race) {
		List<String> ids = race.getAdolescenceSkillRanks().keySet().stream().map(e -> {
			int index = e.indexOf(":");
			if (index > 0) {
				return e.substring(0, index);
			}
			return e;
		}).toList();
		return skillRepository.findByIdsOnNewCharacter(ids);
	}

}
