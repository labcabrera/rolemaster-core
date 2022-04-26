package org.labcabrera.rolemaster.core.service.character.processor;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(CharacterPostProcessor.Orders.DB)
public class CharacterDefensiveBonusPostProcessor implements CharacterPostProcessor {

	@Override
	public void accept(CharacterInfo character) {
		int quBonus = 3 * character.getAttributes().get(AttributeType.QUICKNESS).getTotalBonus();
		int armorPenalty = character.getArmor().getArmorQuPenalty();
		int armorBonus = character.getArmor().getArmorDefensiveBonus();

		int db = quBonus - armorPenalty + armorBonus;
		character.setDefensiveBonus(db);
	}

}
