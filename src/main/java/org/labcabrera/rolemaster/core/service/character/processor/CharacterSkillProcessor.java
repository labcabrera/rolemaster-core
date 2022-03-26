package org.labcabrera.rolemaster.core.service.character.processor;

import org.labcabrera.rolemaster.core.model.character.BonusType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(CharacterPostProcessor.Orders.SKILL)
@Slf4j
public class CharacterSkillProcessor implements CharacterPostProcessor {

	@Override
	public void accept(CharacterInfo character) {
		log.debug("Processing character {}", character.getName());
		character.getSkills().stream().forEach(characterSkill -> {
			//TODO

			int bonusAttribute = 0;
			int attributeBonus = 0;
			characterSkill.getBonus().put(BonusType.RANK, bonusAttribute);
			characterSkill.getBonus().put(BonusType.ATTRIBUTE, attributeBonus);
		});

	}

}
