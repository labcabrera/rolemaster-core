package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.dto.action.execution.WeaponBreakageExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class WeaponBreakageExecutionService {

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private AttackProcessorService processorService;

	@Autowired
	private AttackContextLoader contextLoader;

	public Mono<TacticalActionAttack> apply(TacticalActionAttack action, WeaponBreakageExecution execution) {
		if (action.getState() != TacticalActionState.PENDING_BREAKAGE_RESOLUTION) {
			return Mono.just(action);
		}
		if (action.getBreakageResults() == null || action.getBreakageResults().isEmpty()) {
			throw new BadRequestException("Action has no breakage results.");
		}
		if (action.getBreakageResults().size() != execution.getRoll().size()) {
			throw new BadRequestException("Invalid roll count.");
		}
		return Mono.just(new AttackContext(action))
			.flatMap(contextLoader::apply)
			.map(this::processBreakage)
			.flatMap(processorService::apply)
			.map(AttackContext::getAction)
			.flatMap(actionRepository::save)
			.map(TacticalActionAttack.class::cast);
	}

	private AttackContext processBreakage(AttackContext context) {
		//TODO
		throw new NotImplementedException();
	}

}
