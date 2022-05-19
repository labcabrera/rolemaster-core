package org.labcabrera.rolemaster.core.services.rmss.character.processor;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(CharacterUpdatePostProcessor.Orders.DB)
public class CharacterDefensiveBonusPostProcessor implements CharacterUpdatePostProcessor {

	@Override
	public void accept(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		int quBonus = 3 * character.getAttributes().get(AttributeType.QUICKNESS).getTotalBonus();
		int armorPenalty = character.getArmor().getArmorQuPenalty();
		int armorBonus = character.getArmor().getArmorDefensiveBonus();

		int db = quBonus - armorPenalty + armorBonus;
		character.setDefensiveBonus(db);
	}

}
