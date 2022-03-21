package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.CharacterStatusController;
import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class CharacterStatusControllerImpl implements CharacterStatusController {

	@Override
	public Mono<CharacterStatus> createStatus(String sessionId, String characterId) {
		// TODO Auto-generated method stub
		return null;
	}

}
