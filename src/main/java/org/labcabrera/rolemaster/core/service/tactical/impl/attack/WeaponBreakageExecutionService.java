package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.dto.action.execution.WeaponBreakageExecution;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackContext;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class WeaponBreakageExecutionService {

	public Mono<TacticalAction> apply(TacticalActionAttack action, WeaponBreakageExecution execution) {
		if (action.getState() != TacticalActionState.PENDING_BREAKAGE_RESOLUTION) {
			return Mono.just(action);
		}
		return loadContext(action, execution)
			.flatMap(this::processBreakage)
			.flatMap(this::processAttack);
	}

	private Mono<AttackContext<?>> loadContext(TacticalActionAttack action, WeaponBreakageExecution execution) {
		//TODO
		throw new NotImplementedException();
	}

	private Mono<AttackContext<?>> processBreakage(AttackContext<?> context) {
		//TODO
		throw new NotImplementedException();
	}

	private Mono<TacticalAction> processAttack(AttackContext<?> context) {
		//TODO
		throw new NotImplementedException();
	}

}
