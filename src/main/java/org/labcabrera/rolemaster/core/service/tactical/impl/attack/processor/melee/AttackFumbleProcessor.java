package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee;

import java.util.function.Function;

import org.labcabrera.rolemaster.core.model.character.inventory.CharacterWeapon;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackFumbleResult;
import org.labcabrera.rolemaster.core.model.tactical.action.FumbleType;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AttackFumbleProcessor implements Function<MeleeAttackContext, Mono<MeleeAttackContext>> {

	@Override
	public Mono<MeleeAttackContext> apply(MeleeAttackContext context) {
		log.debug("Checking fumble for attack {}", context.getAction().getId());

		TacticalCharacter source = context.getSource();

		// Primary attack
		Integer primaryRoll = context.getAction().getRoll().getFirstRoll();
		CharacterWeapon primaryWeapon = source.getInventory().getMainHandWeapon();
		AttackFumbleResult primaryFumbleResult = processFumble(primaryWeapon, primaryRoll);
		if (primaryFumbleResult != null) {
			context.getAction().getAttackResult().setFumbleResult(primaryFumbleResult);
		}

		if (context.getAction().isFlumbe()) {
			context.getAction().setState(TacticalActionState.PENDING_FUMBLE_RESOLUTION);
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
