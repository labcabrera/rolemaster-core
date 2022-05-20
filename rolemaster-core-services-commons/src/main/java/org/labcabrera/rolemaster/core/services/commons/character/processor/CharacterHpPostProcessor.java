package org.labcabrera.rolemaster.core.services.commons.character.processor;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(CharacterUpdatePostProcessor.Orders.HP)
@Slf4j
public class CharacterHpPostProcessor implements CharacterUpdatePostProcessor {

	@Override
	public void accept(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		log.debug("Processing character {}", character.getName());

		CharacterSkill skill = character.getSkills().stream()
			.filter(e -> "body-development".equals(e.getSkillId()))
			.findFirst().orElse(new CharacterSkill());

		//TODO
		int professionBonus = 0;
		int maxHp = 10 + skill.getTotalBonus() + professionBonus;
		character.setMaxHp(maxHp);
	}

}
