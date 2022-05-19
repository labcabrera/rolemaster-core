package org.labcabrera.rolemaster.core.services.commons.character.creation.processor;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(CharacterUpdatePostProcessor.Orders.DEV_POINTS)
@Slf4j
class CharacterDevelopmentPointsPostProcessor implements CharacterUpdatePostProcessor {

	@Override
	public void accept(CharacterModificationContext contex) {
		CharacterInfo character = contex.getCharacter();
		log.debug("Processing character {}", character.getName());
		int tmp = 0;
		tmp += character.getAttributes().get(AttributeType.AGILITY).getCurrentValue();
		tmp += character.getAttributes().get(AttributeType.CONSTITUTION).getCurrentValue();
		tmp += character.getAttributes().get(AttributeType.MEMORY).getCurrentValue();
		tmp += character.getAttributes().get(AttributeType.REASONING).getCurrentValue();
		tmp += character.getAttributes().get(AttributeType.SELF_DISCIPLINE).getCurrentValue();
		int dp = tmp / 5;
		character.getDevelopmentPoints().setTotalPoints(dp);
	}

}
