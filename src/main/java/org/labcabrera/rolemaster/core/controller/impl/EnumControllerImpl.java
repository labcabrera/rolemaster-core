package org.labcabrera.rolemaster.core.controller.impl;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.controller.EnumController;
import org.labcabrera.rolemaster.core.dto.NamedKey;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.CodeNameEnum;
import org.labcabrera.rolemaster.core.model.spell.Realm;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackFacing;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.MovementPace;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EnumControllerImpl implements EnumController {

	@Override
	public Mono<List<String>> getEnums() {
		return Mono.just(Arrays.asList("realm", "movement-pace", "melee-attack-type", "melee-attack-facing"));
	}

	@Override
	public Flux<NamedKey> getEnumValues(String enumName) {
		switch (enumName) {
		case "realm":
			return toFlux(Realm.values());
		case "movement-pace":
			return toFlux(MovementPace.values());
		case "melee-attack-type":
			return toFlux(MeleeAttackType.values());
		case "melee-attack-facing":
			return toFlux(MeleeAttackFacing.values());
		default:
			throw new BadRequestException("Invalid enum name");
		}
	}

	private Flux<NamedKey> toFlux(CodeNameEnum[] values) {
		return Flux.just(values)
			.map(value -> NamedKey.builder()
				.key(value.getCode())
				.name(value.getName())
				.build());
	}

}
