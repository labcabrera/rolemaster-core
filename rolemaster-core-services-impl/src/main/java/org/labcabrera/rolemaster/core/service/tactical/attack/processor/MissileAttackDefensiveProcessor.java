package org.labcabrera.rolemaster.core.service.tactical.attack.processor;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class MissileAttackDefensiveProcessor implements AbstractAttackProcessor {

	@Override
	public Mono<AttackContext> apply(AttackContext context) {
		//TODO missile modifiers
		return Mono.just(context);
	}

}
