package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.service.context.loader.AttackContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MeleeAttackExecutionService {

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private AttackContextLoader contextLoader;

	@Autowired
	private AttackProcessorService processorService;

	public Mono<TacticalActionMeleeAttack> execute(TacticalActionMeleeAttack action, MeleeAttackExecution execution) {
		if (action.getState() != TacticalActionState.PENDING) {
			return Mono.just(action);
		}
		log.debug("Processing melee attack");
		loadAction(action, execution);
		loadTargets(action, execution);
		return Mono.just(AttackContext.builder().action(action).build())
			.flatMap(contextLoader::apply)
			.flatMap(processorService::apply)
			.map(AttackContext::getAction)
			.flatMap(actionRepository::save)
			.map(TacticalActionMeleeAttack.class::cast);
	}

	private void loadAction(TacticalActionMeleeAttack action, MeleeAttackExecution execution) {
		action.setRolls(execution.getRolls());
		action.setFacingMap(execution.getFacingMap());
		action.setCustomBonus(execution.getCustomBonus());
	}

	private void loadTargets(TacticalActionMeleeAttack action, MeleeAttackExecution execution) {
		switch (action.getMeleeAttackType()) {
		case FULL:
			if (!execution.getTargets().isEmpty()) {
				throw new BadRequestException("Can not declare target in full melee attack type");
			}
			break;
		case PRESS_AND_MELEE, REACT_AND_MELEE:
			if (execution.getTargets().isEmpty()) {
				throw new BadRequestException("Required target");
			}
			if (action.getMeleeAttackMode() == MeleeAttackMode.TWO_WEAPONS && execution.getTargets().size() != 2) {
				throw new BadRequestException("Expected 2 targets using two weapon attacks");
			}
			action.setTargets(execution.getTargets());
			break;
		default:
			break;
		}
	}

}
