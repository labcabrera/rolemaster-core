package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import reactor.core.publisher.Mono;

public abstract class AbstractAttackProcessor {

	public abstract Mono<AttackContext> apply(AttackContext context);

}