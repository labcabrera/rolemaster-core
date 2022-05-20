package org.labcabrera.rolemaster.core.services.commons.character.processor;

import java.math.BigDecimal;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(CharacterUpdatePostProcessor.Orders.EXHAUSTION_POINTS)
public class CharacterExhaustionPostProcessor implements CharacterUpdatePostProcessor {

	@Override
	public void accept(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		if (character.getVersion() != RolemasterVersion.RMSS) {
			return;
		}
		int bonus = character.getAttributes().get(AttributeType.CONSTITUTION).getTotalBonus();
		int ep = 40 + bonus * 3;
		character.setMaxExhaustionPoints(new BigDecimal(ep));

	}

}
