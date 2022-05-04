package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.service.context.AttackContext;

import reactor.core.publisher.Mono;

public interface AbstractAttackProcessor {

	Mono<AttackContext> apply(AttackContext context);

}
