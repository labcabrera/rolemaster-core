package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackBreakageProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackFumbleProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackResultProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackWeaponTableProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.MeleeAttackDefensiveBonusProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.OffensiveBonusProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MeleeAttackExecutionService {

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private OffensiveBonusProcessor offensiveBonusProcessor;

	@Autowired
	private MeleeAttackDefensiveBonusProcessor defensiveBonusProcessor;

	@Autowired
	private AttackWeaponTableProcessor weaponTableProcessor;

	@Autowired
	private AttackFumbleProcessor fumbleProcessor;

	@Autowired
	private AttackBreakageProcessor breakageProcessor;

	@Autowired
	private AttackResultProcessor attackResultProcessor;

	@Autowired
	private AttackContextLoader contextLoader;

	public Mono<TacticalActionMeleeAttack> execute(TacticalActionMeleeAttack action, MeleeAttackExecution execution) {
		if (action.getState() != TacticalActionState.PENDING) {
			return Mono.just(action);
		}
		loadTargets(action, execution);
		action.setRolls(execution.getRolls());
		action.setFacingMap(execution.getFacingMap());

		AttackContext context = new AttackContext();
		context.setAction(action);

		return Mono.just(context)
			.flatMap(contextLoader::apply)
			.flatMap(fumbleProcessor::apply)
			.flatMap(breakageProcessor::apply)
			.flatMap(offensiveBonusProcessor::apply)
			.flatMap(defensiveBonusProcessor::apply)
			.flatMap(weaponTableProcessor::apply)
			.flatMap(attackResultProcessor::apply)
			.map(AttackContext::getAction)
			.flatMap(actionRepository::save)
			.map(TacticalActionMeleeAttack.class::cast);
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
