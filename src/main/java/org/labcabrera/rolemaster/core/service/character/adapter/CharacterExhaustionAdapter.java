package org.labcabrera.rolemaster.core.service.character.adapter;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.service.character.CharacterAdapter;
import org.springframework.stereotype.Component;

@Component
public class CharacterExhaustionAdapter implements CharacterAdapter {

	@Override
	public void accept(CharacterInfo character) {
		int bonus = character.getAttributes().get(AttributeType.CONSTITUTION).getTotalBonus();
		int ep = 40 + bonus * 3;
		character.setMaxExhaustionPoints(ep);
	}

}
