package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.model.character.inventory.CharacterWeapon;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackFumbleResult;
import org.labcabrera.rolemaster.core.model.tactical.action.FumbleType;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AttackFumbleProcessor {

	public <T extends AttackContext<?>> Mono<T> apply(T context) {
		log.debug("Checking fumble for attack {}", context.getAction().getId());

		TacticalCharacter source = context.getSource();

		// Primary attack
		Integer primaryRoll = context.getAction().getRoll().getFirstRoll();
		CharacterWeapon primaryWeapon = source.getInventory().getMainHandWeapon();
		AttackFumbleResult primaryFumbleResult = processFumble(primaryWeapon, primaryRoll);
		if (primaryFumbleResult != null) {
			context.getAction().getAttackResult().setFumbleResult(primaryFumbleResult);
		}
		// TODO Secondary attack
		return Mono.just(context);
	}

	private AttackFumbleResult processFumble(CharacterWeapon weapon, Integer roll) {
		if (roll > weapon.getFumble()) {
			return null;
		}
		return AttackFumbleResult.builder()
			.type(getFumbleType(weapon))
			.build();
	}

	private FumbleType getFumbleType(CharacterWeapon weapon) {
		//TODO
		return FumbleType.HAND_1H;
	}

}
