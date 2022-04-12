package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile;

import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class MissileAttackLoaderProcessor {

	@Autowired
	private TacticalActionRepository tacticalActionRepository;

	public Mono<TacticalActionMissileAttack> apply(MissileAttackExecution execution) {
		return findAction(execution.getActionId())
			.map(e -> loadExecutionData(e, execution));
	}

	private Mono<TacticalActionMissileAttack> findAction(String actionId) {
		return tacticalActionRepository.findById(actionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Action " + actionId + " not found")))
			.map(e -> {
				try {
					return (TacticalActionMissileAttack) e;
				}
				catch (Exception ex) {
					throw new BadRequestException("Action " + actionId + " is not a valid missile attack");
				}
			});
	}

	private TacticalActionMissileAttack loadExecutionData(TacticalActionMissileAttack action, MissileAttackExecution execution) {
		action.setDistance(execution.getDistance());
		action.setRoll(execution.getRoll());
		action.setCover(execution.getCover());
		return action;
	}
}
