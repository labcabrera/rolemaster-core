package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.TacticalCharacterController;
import org.labcabrera.rolemaster.core.dto.tactical.TacticalCharacterModification;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.services.tactical.TacticalCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TacticalCharacterContextControllerImpl implements TacticalCharacterController {

	@Autowired
	private TacticalCharacterService tacticalCharacterContextService;

	@Override
	public Mono<TacticalCharacter> findById(String id) {
		return tacticalCharacterContextService.findById(id);
	}

	@Override
	public Flux<TacticalCharacter> findAll(Pageable pageable) {
		return tacticalCharacterContextService.findAll(pageable);
	}

	@Override
	public Mono<TacticalCharacter> update(JwtAuthenticationToken auth, String tacticalCharacterId,
		TacticalCharacterModification modification) {
		return tacticalCharacterContextService.update(auth, tacticalCharacterId, modification);
	}

	@Override
	public Mono<Void> delete(String id) {
		return tacticalCharacterContextService.delete(id);
	}

}
