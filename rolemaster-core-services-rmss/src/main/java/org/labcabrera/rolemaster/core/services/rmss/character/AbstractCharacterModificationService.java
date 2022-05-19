package org.labcabrera.rolemaster.core.services.rmss.character;

import java.util.List;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.labcabrera.rolemaster.core.services.commons.context.CharacterModificationContextLoader;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCharacterModificationService {

	@Autowired
	protected CharacterModificationContextLoader contextLoader;

	@Autowired
	protected List<CharacterUpdatePostProcessor> postProcessors;

	protected CharacterModificationContext applyPostProcessors(CharacterModificationContext context) {
		postProcessors.stream().forEach(processor -> processor.accept(context));
		return context;
	}
}
