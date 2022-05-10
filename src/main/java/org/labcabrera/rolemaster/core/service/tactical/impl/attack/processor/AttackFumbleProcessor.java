package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackFumbleResult;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.FumbleType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.service.context.AttackContext;
import org.labcabrera.rolemaster.core.service.context.HasItemMap;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Order(0)
@Component
@Slf4j
public class AttackFumbleProcessor implements AbstractAttackProcessor {

	private static final int NO_WEAPON_FUMBLE = 2;

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	@Autowired
	private TacticalCharacterItemService itemService;

	@Override
	public Mono<AttackContext> apply(AttackContext context) {
		if (context.getAction().getState() != TacticalActionState.PENDING) {
			log.debug("Ignoring attack fumble processor");
			return Mono.just(context);
		}
		log.debug("Processing attack fumble");
		return Mono.just(context)
			.flatMap(this::processMainHandAttack)
			.flatMap(this::processOffHandAttack);
	}

	private Mono<AttackContext> processMainHandAttack(AttackContext context) {
		return fumbleHandAttack(context, AttackTargetType.MAIN_HAND);
	}

	private Mono<AttackContext> processOffHandAttack(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMeleeAttack ma && ma.getMeleeAttackMode() == MeleeAttackMode.TWO_WEAPONS) {
			return fumbleHandAttack(context, AttackTargetType.OFF_HAND);
		}
		return Mono.just(context);
	}

	private Mono<AttackContext> fumbleHandAttack(AttackContext context, AttackTargetType type) {
		CharacterItem weapon = itemResolver.getWeapon(context.getSource(), type);
		int weaponFumble = getFumble(weapon, context);
		int roll = context.getAction().getRolls().get(type).getResult();
		if (roll <= weaponFumble) {
			log.debug("Adding fumble result for attack {}", context.getAction().getId());
			AttackFumbleResult afr = AttackFumbleResult.builder()
				.weaponFumble(weaponFumble)
				.type(getFumbleType(context, weapon))
				.build();
			context.getAction().getFumbleResults().put(type, afr);
			context.getAction().setState(TacticalActionState.PENDING_FUMBLE_RESOLUTION);
		}
		return Mono.just(context);
	}

	private Integer getFumble(CharacterItem item, HasItemMap hasItemMap) {
		if (item == null) {
			return NO_WEAPON_FUMBLE;
		}
		return itemService.getFumble(item, hasItemMap);
	}

	private FumbleType getFumbleType(AttackContext context, CharacterItem item) {
		//TODO check weapon type
		return FumbleType.WEAPON_1H;
	}

}
