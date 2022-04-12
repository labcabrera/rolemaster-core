package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile;

import java.util.function.Function;

import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMissileAttack;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class MissileAttackDefensiveProcessor implements Function<TacticalActionMissileAttack, Mono<TacticalActionMissileAttack>> {

	@Override
	public Mono<TacticalActionMissileAttack> apply(TacticalActionMissileAttack action) {
		return Mono.just(action);
	}

}
