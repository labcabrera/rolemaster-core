package org.labcabrera.rolemaster.core.service.tactical.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.converter.TacticalActionConverter;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackCriticalExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalActionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.AttackResultProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.CriticalAttackExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.MeleeAttackExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalActionServiceImpl implements TacticalActionService {

	@Autowired
	private TacticalActionConverter actionConverter;

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private MeleeAttackExecutionService meleeExecutionService;

	@Autowired
	private CriticalAttackExecutionService criticalAttackExecutionService;

	@Autowired
	private AttackResultProcessor attackResultProcessor;

	@Override
	public Mono<TacticalAction> getDeclaredAction(String actionId) {
		return actionRepository.findById(actionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Action not found")));
	}

	@Override
	public Mono<Void> removeDeclaredAction(String actionId) {
		return actionRepository.findById(actionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Action not found")))
			//TODO check round state
			.flatMap(actionRepository::delete);
	}

	@Override
	public Mono<TacticalAction> delare(@Valid TacticalActionDeclaration actionDeclaration) {
		TacticalAction ta = actionConverter.convert(actionDeclaration);
		return Mono.just(ta)
			.zipWith(actionRepository.existsByRoundIdAndSourceAndPriority(ta.getRoundId(), ta.getSource(), ta.getPriority()))
			.map(pair -> {
				if (pair.getT2()) {
					throw new BadRequestException("Duplicate action declaration");
				}
				TacticalAction action = pair.getT1();
				action.setState(TacticalActionState.PENDING);
				return action;
			})
			.flatMap(actionRepository::save);
	}

	@Override
	public Mono<TacticalAction> execute(TacticalActionExecution request) {
		return actionRepository.findById(request.getActionId())
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Action not found")))
			.flatMap(action -> {
				if (action instanceof TacticalActionMeleeAttack) {
					if (!(request instanceof MeleeAttackExecution)) {
						throw new BadRequestException("Expected melee attack execution");
					}
					return meleeExecutionService.execute((TacticalActionMeleeAttack) action, (MeleeAttackExecution) request);
				}
				throw new RuntimeException("Not implemented");
			});
	}

	@Override
	public Mono<TacticalAction> executeCritical(MeleeAttackCriticalExecution request) {
		return actionRepository.findById(request.getActionId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Action not found")))
			.map(e -> criticalAttackExecutionService.apply(e, request))
			.flatMap(actionRepository::save)
			.map(e -> (TacticalActionAttack) e)
			.flatMap(e -> attackResultProcessor.apply(e));
	}

}
