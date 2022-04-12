package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class MissileAttackDefensiveProcessor implements Function<MissileAttackContext, Mono<MissileAttackContext>> {

	@Override
	public Mono<MissileAttackContext> apply(MissileAttackContext action) {
		return Mono.just(action);
	}

}
