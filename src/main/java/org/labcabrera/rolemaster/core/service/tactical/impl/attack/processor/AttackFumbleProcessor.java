package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackFumbleResult;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.FumbleType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AttackFumbleProcessor {

	private static final int NO_WEAPON_FUMBLE = 2;

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	@Autowired
	private TacticalCharacterItemService itemService;

	public <T extends AttackContext<?>> Mono<T> apply(T context) {
		return Mono.just(context)
			.flatMap(this::processMainHandAttack)
			.flatMap(this::processOffHandAttack);
	}

	private <T extends AttackContext<?>> Mono<T> processMainHandAttack(T context) {
		return fumbleHandAttack(context, AttackTargetType.MAIN_HAND);
	}

	private <T extends AttackContext<?>> Mono<T> processOffHandAttack(T context) {
		if (context.getAction()instanceof TacticalActionMeleeAttack meleeAttack) {
			if (meleeAttack.getMeleeAttackMode() == MeleeAttackMode.TWO_WEAPONS) {
				return fumbleHandAttack(context, AttackTargetType.OFF_HAND);
			}
		}
		return Mono.just(context);
	}

	private <T extends AttackContext<?>> Mono<T> fumbleHandAttack(T context, AttackTargetType type) {
		CharacterItem weapon = itemResolver.getWeapon(context.getSource(), type);
		return Mono.just(context)
			.zipWith(getFumble(weapon))
			.map(pair -> {
				int weaponFumble = pair.getT2();
				int roll = context.getAction().getRolls().get(type).getResult();
				if (roll <= weaponFumble) {
					log.debug("Adding fumble result for attack {}", context.getAction().getId());
					AttackFumbleResult afr = AttackFumbleResult.builder()
						.weaponFumble(weaponFumble)
						.type(getFumbleType(weapon))
						.build();
					context.getAction().getFumbleResults().put(type, afr);
					context.getAction().setState(TacticalActionState.PENDING_FUMBLE_RESOLUTION);
				}
				return context;
			});
	}

	private Mono<Integer> getFumble(CharacterItem item) {
		if (item == null) {
			return Mono.just(NO_WEAPON_FUMBLE);
		}
		return itemService.getFumble(item);
	}

	private FumbleType getFumbleType(CharacterItem item) {
		//TODO check weapon type
		return FumbleType.HAND_1H;
	}

}
