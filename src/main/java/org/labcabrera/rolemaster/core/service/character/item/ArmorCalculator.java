package org.labcabrera.rolemaster.core.service.character.item;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.service.character.processor.CharacterArmorPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArmorCalculator {

	@Autowired
	private CharacterArmorPostProcessor armorProcessor;

	public <E> E apply(E data, CharacterInfo character) {
		armorProcessor.accept(character);
		return data;
	}
}
