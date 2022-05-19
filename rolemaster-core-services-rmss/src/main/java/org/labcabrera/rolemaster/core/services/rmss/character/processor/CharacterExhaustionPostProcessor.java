package org.labcabrera.rolemaster.core.services.rmss.character.processor;

import java.math.BigDecimal;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(CharacterUpdatePostProcessor.Orders.EXHAUSTION_POINTS)
@Slf4j
public class CharacterExhaustionPostProcessor implements CharacterUpdatePostProcessor {

	@Override
	public void accept(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		log.debug("Processing character {}", character.getName());
		int bonus = character.getAttributes().get(AttributeType.CONSTITUTION).getTotalBonus();
		int ep = 40 + bonus * 3;
		character.setMaxExhaustionPoints(new BigDecimal(ep));

	}

}
