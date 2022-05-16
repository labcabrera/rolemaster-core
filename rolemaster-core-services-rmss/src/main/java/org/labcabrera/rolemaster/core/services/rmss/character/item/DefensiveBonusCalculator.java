package org.labcabrera.rolemaster.core.services.rmss.character.item;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.services.rmss.character.processor.CharacterDefensiveBonusPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class DefensiveBonusCalculator {

	@Autowired
	private CharacterDefensiveBonusPostProcessor dbProcessor;

	public <E> E apply(E data, CharacterInfo character) {
		dbProcessor.accept(character);
		return data;
	}
}
