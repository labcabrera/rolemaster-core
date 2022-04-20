package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
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
		return Mono.just(context)
			.flatMap(this::processMainHandAttack)
			.flatMap(this::processOffHandAttack);
	}

	private <T extends AttackContext<?>> Mono<T> processMainHandAttack(T context) {
		CharacterItem mainItem = context.getSource().getItems().stream()
			.filter(e -> e.getPosition() == ItemPosition.MAIN_HAND)
			.findFirst().orElse(null);
		//TODO read
		Integer fumble = 4;
		Integer primaryRoll = context.getAction().getRoll().getFirstRoll();
		if (primaryRoll <= fumble) {
			AttackFumbleResult afr = AttackFumbleResult.builder()
				.type(getFumbleType(mainItem))
				.build();
			context.getAction().getFumbleResults().add(afr);
		}
		return Mono.just(context);
	}

	private <T extends AttackContext<?>> Mono<T> processOffHandAttack(T context) {
		CharacterItem offHandItem = context.getSource().getItems().stream()
			.filter(e -> e.getPosition() == ItemPosition.OFF_HAND)
			.findFirst().orElse(null);
		//TODO
		log.debug("TODO: process offhand fumble: {}", offHandItem);
		return Mono.just(context);
	}

	private FumbleType getFumbleType(CharacterItem item) {
		//TODO
		return FumbleType.HAND_1H;
	}

}
