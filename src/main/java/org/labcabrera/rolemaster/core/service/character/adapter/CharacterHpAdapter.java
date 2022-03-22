package org.labcabrera.rolemaster.core.service.character.adapter;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(200)
@Slf4j
public class CharacterHpAdapter implements CharacterAdapter {

	@Override
	public void accept(CharacterInfo character) {
		log.debug("Loading max HP for {}", character.getId() == null ? "new character" : "character " + character.getId());

		CharacterSkill skill = character.getSkills().stream()
			.filter(e -> "body-development".equals(e.getSkillId()))
			.findFirst().orElse(CharacterSkill.builder().build());

		//TODO
		int professionBonus = 0;
		int maxHp = 10 + skill.getTotalBonus() + professionBonus;
		character.setMaxHp(maxHp);
	}

}
