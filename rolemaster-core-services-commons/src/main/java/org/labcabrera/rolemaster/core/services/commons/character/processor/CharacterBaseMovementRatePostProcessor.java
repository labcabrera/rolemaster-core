package org.labcabrera.rolemaster.core.services.commons.character.processor;

import java.util.List;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.BaseMovementRate;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.services.character.CharacterBaseMovementRateService;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.labcabrera.rolemaster.core.services.commons.VersionRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(CharacterUpdatePostProcessor.Orders.BASE_MOVEMENT)
@ConditionalOnBean(CharacterBaseMovementRateService.class)
class CharacterBaseMovementRatePostProcessor implements CharacterUpdatePostProcessor {

	@Autowired
	private List<CharacterBaseMovementRateService> services;

	@Autowired
	private VersionRouter router;

	@Override
	public void accept(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		RolemasterVersion version = character.getVersion();
		Race race = context.getRace();
		BaseMovementRate baseMovementRate = router.route(version, services).getBaseMovementRate(character, race);
		character.setBaseMovementRate(baseMovementRate);
	}

}
