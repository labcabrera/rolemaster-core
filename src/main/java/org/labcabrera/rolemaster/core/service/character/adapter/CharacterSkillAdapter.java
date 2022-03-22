package org.labcabrera.rolemaster.core.service.character.adapter;

import org.labcabrera.rolemaster.core.exception.CharacterCreationException;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.Skill;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(100)
@Slf4j
public class CharacterSkillAdapter implements CharacterAdapter {

	@Override
	public CharacterModificationContext apply(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		log.debug("Loading skills for {}", character.getId() == null ? "new character" : "character " + character.getId());
		context.getCharacter().getSkills().stream().forEach(characterSkill -> {
			String skillId = characterSkill.getSkillId();
			Skill skill = context.getSkills().stream()
				.filter(e -> e.getId().equals(skillId))
				.findFirst().orElseThrow(() -> new CharacterCreationException("Missing skill " + skillId));
			loadAttributeBonus(character, characterSkill, skill);
		});
		return context;
	}

	private void loadAttributeBonus(CharacterInfo character, CharacterSkill characterSkill, Skill skill) {
		int bonus = 0;
		if (skill.getAttributeBonus() != null) {
			for (AttributeType i : skill.getAttributeBonus()) {
				bonus += character.getAttributes().get(i).getTotalBonus();
			}
		}
		else {
			log.debug("Missing skill {} attribute bonus", skill.getId());
		}
		characterSkill.setAttributeBonus(bonus);
	}

}
