package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackBreakageResult;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.service.tactical.attack.processor.AbstractAttackProcessor;
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
public class AttackBreakageProcessor implements AbstractAttackProcessor {

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	@Autowired
	private TacticalCharacterItemService itemService;

	@Override
	public Mono<AttackContext> apply(AttackContext context) {
		if (context.getAction().getState() != TacticalActionState.PENDING) {
			log.debug("Ignoring weapon brekage processor (state: {})", context.getAction().getState());
			return Mono.just(context);
		}
		if (!context.getAction().getBreakageResults().isEmpty()) {
			return Mono.just(context);
		}
		log.debug("Processing weapon breakage");
		return Mono.just(context)
			.map(ctx -> apply(ctx, AttackTargetType.MAIN_HAND))
			.map(ctx -> apply(ctx, AttackTargetType.OFF_HAND));
	}

	private AttackContext apply(AttackContext context, AttackTargetType type) {
		if (!context.getAction().getRolls().containsKey(type) || context.getAction().getSpecialAttack() != null) {
			return context;
		}
		TacticalCharacter tc = context.getSource();
		OpenRoll roll = context.getAction().getRolls().get(type);
		ItemPosition position = type == AttackTargetType.OFF_HAND ? ItemPosition.OFF_HAND : ItemPosition.MAIN_HAND;
		CharacterItem item = itemResolver.getItem(tc, position);
		if (itemService.isUnbreakable(item)) {
			return context;
		}
		Integer breakage = itemService.getBreakage(item, context);
		Integer strength = itemService.getStrength(item, context);
		if (breakage == null || strength == null) {
			log.warn("Undefined Breakage ({}) or strength ({})");
			return context;
		}
		if (checkBreakage(breakage, roll)) {
			AttackBreakageResult result = AttackBreakageResult.builder()
				.breakage(breakage)
				.strength(strength)
				.build();
			context.getAction().getBreakageResults().put(type, result);
			context.getAction().setState(TacticalActionState.PENDING_BREAKAGE_RESOLUTION);
		}
		return context;
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
