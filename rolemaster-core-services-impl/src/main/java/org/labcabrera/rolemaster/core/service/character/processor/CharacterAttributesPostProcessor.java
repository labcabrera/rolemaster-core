package org.labcabrera.rolemaster.core.service.character.processor;

import org.labcabrera.rolemaster.core.model.character.AttributeBonusType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.service.character.CharacterUpdatePostProcessor;
import org.labcabrera.rolemaster.core.table.attribute.AttributeBonusTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(CharacterUpdatePostProcessor.Orders.ATTRIBUTE)
@Slf4j
class CharacterAttributesPostProcessor implements CharacterUpdatePostProcessor {

	@Autowired
	private AttributeBonusTable attributeBonusTable;

	@Override
	public void accept(CharacterInfo character) {
		log.debug("Processing character {}", character.getName());
		character.getAttributes().keySet().forEach(attributeType -> {
			int baseBonus = attributeBonusTable.getBonus(character.getAttributes().get(attributeType).getCurrentValue());
			CharacterAttribute ca = character.getAttributes().get(attributeType);
			ca.getBonus().put(AttributeBonusType.ATTRIBUTE, baseBonus);
		});
	}

}
