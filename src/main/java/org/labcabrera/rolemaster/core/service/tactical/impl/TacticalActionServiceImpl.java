package org.labcabrera.rolemaster.core.service.tactical.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.converter.TacticalActionConverter;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.FumbleExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.WeaponBreakageExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.message.Messages.Errors;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.context.AttackContext;
import org.labcabrera.rolemaster.core.service.tactical.TacticalActionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.CriticalAttackExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.FumbleAttackExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.WeaponBreakageExecutionService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackResultProcessor;
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

	@Autowired
	private WeaponBreakageExecutionService breakageExecutionService;

	@Override
	public Mono<TacticalAction> getDeclaredAction(String actionId) {
		return actionRepository.findById(actionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.missingAction(actionId))));
	}

	@Override
	public Mono<Void> removeDeclaredAction(String actionId) {
		return actionRepository.findById(actionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.missingAction(actionId))))
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
				if (Boolean.TRUE.equals(pair.getT2())) {
					throw new BadRequestException("Duplicate action declaration");
				}
				TacticalAction action = pair.getT1();
				action.setState(TacticalActionState.PENDING);
				return action;
			})
			.flatMap(actionRepository::save);
	}

	@Override
	public Mono<TacticalAction> execute(String actionId, TacticalActionExecution request) {
		return actionRepository.findById(actionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.missingAction(actionId))))
			.flatMap(action -> this.tacticalActionExecutionRouter.execute(action, request));
	}

	@Override
	public Mono<TacticalAction> executeCritical(String actionId, AttackCriticalExecution request) {
		return actionRepository.findById(actionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.missingAction(actionId))))
			.map(e -> criticalAttackExecutionService.apply(e, request))
			.flatMap(actionRepository::save)
			.map(TacticalActionAttack.class::cast)
			.map(e -> AttackContext.builder().action(e).build())
			.flatMap(attackResultProcessor::apply)
			.map(AttackContext::getAction);
	}

	@Override
	public Mono<TacticalAction> executeFumble(String actionId, FumbleExecution execution) {
		return actionRepository.findById(actionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.missingAction(actionId))))
			.map(TacticalActionAttack.class::cast)
			.map(e -> fumbleAttackExecutionService.apply(e, execution))
			//TODO Process fumble result
			.flatMap(actionRepository::save);
	}

	@Override
	public Mono<TacticalAction> executeBreakage(String actionId, WeaponBreakageExecution execution) {
		return actionRepository.findById(actionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.missingAction(actionId))))
			.map(TacticalActionAttack.class::cast)
			.flatMap(attack -> breakageExecutionService.apply(attack, execution));
	}

}
