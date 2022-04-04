package org.labcabrera.rolemaster.core.controller.impl;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.controller.EnumController;
import org.labcabrera.rolemaster.core.dto.NamedKey;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.spell.Realm;
import org.labcabrera.rolemaster.core.model.tactical.actions.MovementPace;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EnumControllerImpl implements EnumController {

	@Override
	public Mono<List<String>> getEnums() {
		return Mono.just(Arrays.asList("realm", "movement-pace"));
	}

	@Override
	public Flux<NamedKey> getEnumValues(String enumName) {
		switch (enumName) {
		case "realm":
			return Flux.just(Realm.values()).map(e -> NamedKey.builder().key(e.getName()).name(e.getDescription()).build());
		case "movement-pace":
			return Flux.just(MovementPace.values()).map(e -> NamedKey.builder().key(e.getName()).name(e.getDescription()).build());
		default:
			throw new BadRequestException("Invalid enum name");
		}

	}

}
