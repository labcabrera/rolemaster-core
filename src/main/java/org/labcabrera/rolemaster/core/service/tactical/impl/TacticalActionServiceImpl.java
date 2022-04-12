package org.labcabrera.rolemaster.core.service.tactical.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.converter.TacticalActionConverter;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.FumbleExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalActionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.CriticalAttackExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.FumbleAttackExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee.AttackResultProcessor;
import org.labcabrera.rolemaster.core.validation.ValidationConstants;
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
	private CriticalAttackExecutionService criticalAttackExecutionService;

	@Autowired
	private FumbleAttackExecutionService fumbleAttackExecutionService;

	@Autowired
	private AttackResultProcessor attackResultProcessor;

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	@Autowired
	private TacticalActionExecutionRouter tacticalActionExecutionRouter;

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

		return tacticalCharacterRepository.findById(actionDeclaration.getSource())
			.switchIfEmpty(Mono.error(() -> new BadRequestException(ValidationConstants.INVALID_ACTION_SOURCE_NOT_FOUND)))
			.then(Mono.just(ta))
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
			.flatMap(action -> this.tacticalActionExecutionRouter.execute(action, request));
	}

	@Override
	public Mono<TacticalAction> executeCritical(AttackCriticalExecution request) {
		return actionRepository.findById(request.getActionId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Action not found")))
			.map(e -> criticalAttackExecutionService.apply(e, request))
			.flatMap(actionRepository::save)
			.map(e -> (TacticalActionAttack) e)
			.flatMap(e -> attackResultProcessor.apply(e));
	}

	@Override
	public Mono<TacticalAction> executeFumble(FumbleExecution execution) {
		return actionRepository.findById(execution.getActionId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Action not found")))
			.map(e -> (TacticalActionAttack) e)
			.map(e -> fumbleAttackExecutionService.apply(e, execution))
			//TODO Process fumble result
			.flatMap(actionRepository::save);
	}

}
