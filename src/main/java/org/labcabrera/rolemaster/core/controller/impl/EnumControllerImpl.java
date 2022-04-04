package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.EnumController;
import org.labcabrera.rolemaster.core.dto.NamedKey;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.spell.Realm;
import org.labcabrera.rolemaster.core.model.tactical.actions.MovementPace;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class EnumControllerImpl implements EnumController {

	@Override
	public Flux<String> getEnums() {
		return Flux.just("realm", "movement-pace");
	}

	@Override
	public Flux<NamedKey> getEnumValues(String enumName) {
		switch (enumName) {
		case "realm":
			return Flux.just(Realm.values()).map(e -> NamedKey.builder().key(e.getName()).name(e.getDescription()).build());
		case "movement-pace":
			return Flux.just(MovementPace.values()).map(e -> NamedKey.builder().key(e.toString()).name(e.getDescription()).build());
		default:
			throw new BadRequestException("Invalid enum name");
		}

	}

}
