package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MeleeAttackExecutionService {

	@Autowired
	private TacticalCharacterContextRepository tacticalCharacterRepository;

	@Autowired
	private TacticalActionRepository actionRepository;

	public Mono<TacticalActionMeleeAttack> execute(TacticalActionMeleeAttack action, MeleeAttackExecution execution) {
		loadTarget(action, execution);

		MeleeAttackContext context = MeleeAttackContext.builder()
			.action(action)
			.execution(execution)
			.build();

		Mono.just(context)
			.zipWith(tacticalCharacterRepository.findById(context.getAction().getSource()))
			.map(pair -> {
				pair.getT1().setSource(pair.getT2());
				return pair.getT1();
			})
			.zipWith(tacticalCharacterRepository.findById(context.getAction().getTarget()))
			.map(pair -> {
				pair.getT1().setTarget(pair.getT2());
				return pair.getT1();
			})
			.zipWith(actionRepository.findByRoundId(action.getRoundId()).collectList())
			.map(pair -> {
				pair.getT1().setActions(pair.getT2());
				return pair.getT1();
			});

		throw new NotImplementedException();
	}

	private void loadTarget(TacticalActionMeleeAttack action, MeleeAttackExecution execution) {
		switch (action.getMeleeAttackType()) {
		case FULL:
			if (execution.getTarget() != null) {
				throw new BadRequestException("Can not declare target in full melee attack type");
			}
		case PRESS_AND_MELEE:
		case REACT_AND_MELEE:
			if (execution.getTarget() == null) {
				throw new BadRequestException("Required target");
			}
			action.setTarget(execution.getTarget());
		default:
			break;
		}
	}

}
