package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import reactor.core.publisher.Mono;

public abstract class AbstractAttackProcessor {

	public abstract <T extends AttackContext<?>> Mono<T> apply(T context);

}
