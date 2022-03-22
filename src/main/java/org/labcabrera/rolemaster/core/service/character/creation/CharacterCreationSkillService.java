package org.labcabrera.rolemaster.core.service.character.creation;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.Skill;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CharacterCreationSkillService {

	@Autowired
	private SkillRepository skillRepository;

	public Mono<CharacterModificationContext> initialize(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		skillRepository.findSkillsOnNewCharacter()
			.map(skill -> loadSkill(character, skill))
			.map(e -> context);
		return Mono.just(context);
	}

	private CharacterInfo loadSkill(CharacterInfo character, Skill skill) {
		CharacterSkill ci = CharacterSkill.builder()
			.skillId(skill.getId())
			.build();
		character.getSkills().add(ci);
		return character;
	}
}
