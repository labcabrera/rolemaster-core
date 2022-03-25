package org.labcabrera.rolemaster.core.service.character.adapter;

import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.table.attribute.AttributeBonusTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterAttributesAdapter implements CharacterAdapter {

	@Autowired
	private AttributeBonusTable attributeBonusTable;

	@Override
	public CharacterModificationContext apply(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		Race race = context.getRace();

		character.getAttributes().keySet().forEach(attributeType -> {
			int baseBonus = attributeBonusTable.getBonus(character.getAttributes().get(attributeType).getCurrentValue());
			int racialBonus = race.getAttributeModifiers().containsKey(attributeType) ? race.getAttributeModifiers().get(attributeType) : 0;
			int specialBonus = 0;
			int totalBonus = baseBonus + racialBonus + specialBonus;
			CharacterAttribute ca = character.getAttributes().get(attributeType);
			ca.setBaseBonus(baseBonus);
			ca.setRacialBonus(racialBonus);
			ca.setSpecialBonus(specialBonus);
			ca.setTotalBonus(totalBonus);
		});
		return context;
	}

}
