package org.labcabrera.rolemaster.core.services.tactical.attack.processor;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;

import reactor.core.publisher.Mono;

public interface AbstractAttackProcessor {

	Mono<AttackContext> apply(AttackContext context);

}
