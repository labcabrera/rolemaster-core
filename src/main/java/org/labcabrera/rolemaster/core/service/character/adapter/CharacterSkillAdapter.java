package org.labcabrera.rolemaster.core.service.character.adapter;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.Skill;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(100)
@Slf4j
public class CharacterSkillAdapter implements CharacterAdapter {

	@Autowired
	private SkillRepository skillRepository;

	@Override
	public void accept(CharacterInfo character) {
		log.debug("Loading skills for {}", character.getId() == null ? "new character" : "character " + character.getId());
		character.getSkills().stream().forEach(e -> loadSkill(character, e));
	}

	private void loadSkill(CharacterInfo character, CharacterSkill characterSkill) {
		String skillId = characterSkill.getSkillId();
		skillRepository
			.findById(skillId)
			.map(skill -> {
				loadAttributeBonus(character, characterSkill, skill);
				return characterSkill;
			})
			.doOnNext(e -> log.debug("Loaded skill {}", e))
			.subscribe();
	}

	private void loadAttributeBonus(CharacterInfo character, CharacterSkill characterSkill, Skill skill) {
		int bonus = 0;
		if (skill.getAttributeBonus() != null) {
			for (AttributeType i : skill.getAttributeBonus()) {
				bonus += character.getAttributes().get(i).getTotalBonus();
			}
		}
		characterSkill.setAttributeBonus(bonus);
	}

}
