package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.actions.AttackResult;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee.AttackFumbleProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee.AttackResultProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee.MeleeAttackDefensiveBonusProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee.MeleeAttackOffensiveBonusProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee.MeleeAttackServiceWeaponTableProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MeleeAttackExecutionService {

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private MeleeAttackOffensiveBonusProcessor offensiveBonusProcessor;

	@Autowired
	private MeleeAttackDefensiveBonusProcessor defensiveBonusProcessor;

	@Autowired
	private MeleeAttackServiceWeaponTableProcessor weaponTableProcessor;

	@Autowired
	private AttackFumbleProcessor fumbleProcessor;

	@Autowired
	private AttackResultProcessor attackResultProcessor;

	public Mono<TacticalActionMeleeAttack> execute(TacticalActionMeleeAttack action, MeleeAttackExecution execution) {
		loadTarget(action, execution);
		action.setPrimaryAttackResult(new AttackResult());

		TacticalAttackContext context = TacticalAttackContext.builder()
			.action(action)
			.execution(execution)
			.build();
		return Mono.just(context)
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
			.flatMap(fumbleProcessor::apply)
			.flatMap(offensiveBonusProcessor::apply)
			.flatMap(defensiveBonusProcessor::apply)
			.map(weaponTableProcessor)
			.flatMap(ctx -> attackResultProcessor.apply(ctx.getAction()))
			.flatMap(actionRepository::save)
			.map(e -> (TacticalActionMeleeAttack) e);
	}

	private void loadTarget(TacticalActionMeleeAttack action, MeleeAttackExecution execution) {
		switch (action.getMeleeAttackType()) {
		case FULL:
			if (execution.getPrimaryTarget() != null) {
				throw new BadRequestException("Can not declare target in full melee attack type");
			}
		case PRESS_AND_MELEE:
		case REACT_AND_MELEE:
			if (execution.getPrimaryTarget() == null) {
				throw new BadRequestException("Required target");
			}
			action.setTarget(execution.getPrimaryTarget());
		default:
			break;
		}
	}

}
