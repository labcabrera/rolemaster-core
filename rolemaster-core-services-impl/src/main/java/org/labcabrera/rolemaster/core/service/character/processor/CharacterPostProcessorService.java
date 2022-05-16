package org.labcabrera.rolemaster.core.service.character.processor;

import java.util.List;
import java.util.function.UnaryOperator;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.service.character.CharacterUpdatePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterPostProcessorService implements UnaryOperator<CharacterInfo> {

	@Autowired
	private List<CharacterUpdatePostProcessor> processors;

	@Override
	public CharacterInfo apply(CharacterInfo character) {
		processors.stream().forEach(processor -> processor.accept(character));
		return character;
	}

}
