package org.labcabrera.rolemaster.core.api.controller.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.labcabrera.rolemaster.core.api.controller.EnumController;
import org.labcabrera.rolemaster.core.dto.NamedKey;
import org.labcabrera.rolemaster.core.model.CodeNameEnum;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.combat.Cover;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;
import org.labcabrera.rolemaster.core.model.maneuver.MovingManeuverCombatSituation;
import org.labcabrera.rolemaster.core.model.spell.Realm;
import org.labcabrera.rolemaster.core.model.tactical.TemperatureMultiplier;
import org.labcabrera.rolemaster.core.model.tactical.TerrainType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackFacing;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.MovementPace;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EnumControllerImpl implements EnumController {

	private Map<String, CodeNameEnum[]> map = new LinkedHashMap<>();

	public EnumControllerImpl() {
		map.put("cover", Cover.values());
		map.put("item-position", ItemPosition.values());
		map.put("melee-attack-facing", MeleeAttackFacing.values());
		map.put("melee-attack-mode", MeleeAttackMode.values());
		map.put("melee-attack-type", MeleeAttackType.values());
		map.put("maneuver-difficulty", ManeuverDifficulty.values());
		map.put("movement-pace", MovementPace.values());
		map.put("moving-maneuver-combat-situation", MovingManeuverCombatSituation.values());
		map.put("realm", Realm.values());
		map.put("terrain", TerrainType.values());
		map.put("temperature-multiplier", TemperatureMultiplier.values());
	}

	@Override
	public Mono<Set<String>> getEnums() {
		return Mono.just(map.keySet());
	}

	@Override
	public Flux<NamedKey> getEnumValues(String enumName) {
		if (!map.containsKey(enumName)) {
			throw new BadRequestException(String.format("Invalid enum name %s.", enumName));
		}
		return toFlux(map.get(enumName));
	}

	private Flux<NamedKey> toFlux(CodeNameEnum[] values) {
		return Flux.just(values)
			.map(value -> NamedKey.builder()
				.key(value.getCode())
				.name(value.getName())
				.build());
	}

}
