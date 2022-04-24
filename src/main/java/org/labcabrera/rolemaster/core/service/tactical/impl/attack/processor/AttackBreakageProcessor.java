package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackBreakageResult;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Order(1)
@Component
@Slf4j
public class AttackBreakageProcessor extends AbstractAttackProcessor {

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	@Autowired
	private TacticalCharacterItemService itemService;

	@Override
	public Mono<AttackContext> apply(AttackContext context) {
		if (context.getAction().getState() != TacticalActionState.PENDING) {
			log.debug("Ignoring weapon brekage processor");
			return Mono.just(context);
		}
		log.debug("Processing weapon breakage");
		return Mono.just(context)
			.flatMap(ctx -> apply(ctx, AttackTargetType.MAIN_HAND))
			.flatMap(ctx -> apply(ctx, AttackTargetType.OFF_HAND));
	}

	private Mono<AttackContext> apply(AttackContext context, AttackTargetType type) {
		if (!context.getAction().getRolls().containsKey(type)) {
			return Mono.just(context);
		}
		TacticalCharacter tc = context.getSource();
		OpenRoll roll = context.getAction().getRolls().get(type);
		ItemPosition position = type == AttackTargetType.OFF_HAND ? ItemPosition.OFF_HAND : ItemPosition.MAIN_HAND;
		CharacterItem item = itemResolver.getItem(tc, position);
		if (itemService.isUnbreakable(item)) {
			return Mono.just(context);
		}
		return Mono.zip(Mono.just(context), itemService.getBreakage(item), itemService.getStrength(item))
			.map(tuple -> {
				Integer breakage = tuple.getT2();
				Integer strength = tuple.getT3();
				if (breakage == null || strength == null) {
					log.warn("Undefined Breakage ({}) or strength ({})");
					return tuple.getT1();
				}
				if (checkBreakage(breakage, roll)) {
					AttackBreakageResult result = AttackBreakageResult.builder()
						.breakage(breakage)
						.strength(strength)
						.build();
					context.getAction().getBreakageResults().put(type, result);
					context.getAction().setState(TacticalActionState.PENDING_BREAKAGE_RESOLUTION);
				}
				return tuple.getT1();
			});
	}

	public boolean checkBreakage(Integer breakage, OpenRoll openRoll) {
		int roll = openRoll.getRolls() != null && !openRoll.getRolls().isEmpty() ? openRoll.getFirstRoll() : openRoll.getResult();
		if (roll > 100) {
			return false;
		}
		int checkModule = roll % 11;
		if (checkModule != 0) {
			return false;
		}
		int checkValue = roll / 11;
		return checkValue <= breakage;
	}

}
