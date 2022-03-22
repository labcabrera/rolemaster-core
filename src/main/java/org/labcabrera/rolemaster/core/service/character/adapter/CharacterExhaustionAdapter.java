package org.labcabrera.rolemaster.core.service.character.adapter;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(201)
@Slf4j
public class CharacterExhaustionAdapter implements CharacterAdapter {

	@Override
	public void accept(CharacterInfo character) {
		log.debug("Loading exhaustion points for {}", character.getId() == null ? "new character" : "character " + character.getId());
		int bonus = character.getAttributes().get(AttributeType.CONSTITUTION).getTotalBonus();
		int ep = 40 + bonus * 3;
		character.setMaxExhaustionPoints(ep);
	}

}
