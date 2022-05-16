package org.labcabrera.rolemaster.core.service.character.processor;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.service.character.CharacterUpdatePostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(CharacterUpdatePostProcessor.Orders.RESISTANCE)
@Slf4j
public class CharacterResistancePostProcessor implements CharacterUpdatePostProcessor {

	@Override
	public void accept(CharacterInfo character) {
		log.debug("Processing character {}", character.getName());
		// TODO Auto-generated method stub
	}

}
