package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import java.util.EnumMap;

import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackExhaustionProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackResultProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackWeaponTableProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.OffensiveBonusProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MissileAttackExecutionService {

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private OffensiveBonusProcessor offensiveBonusProcessor;

	@Autowired
	private AttackWeaponTableProcessor attackWeaponTableProcessor;

	@Autowired
	private AttackResultProcessor attackResultProcessor;

	@Autowired
	private AttackExhaustionProcessor exhaustionProcessor;

	@Autowired
	private AttackContextLoader contextLoader;

	public Mono<TacticalActionMissileAttack> execute(TacticalActionMissileAttack action, MissileAttackExecution execution) {
		action.setDistance(execution.getDistance());
		action.setCover(execution.getCover());
		action.setRolls(new EnumMap<>(AttackTargetType.class));
		action.getRolls().put(AttackTargetType.MAIN_HAND, execution.getRoll());

		AttackContext context = new AttackContext();
		context.setAction(action);

		return Mono.just(context)
			.flatMap(contextLoader::apply)
			.flatMap(offensiveBonusProcessor::apply)
			.flatMap(attackWeaponTableProcessor::apply)
			.flatMap(exhaustionProcessor::apply)
			.flatMap(act -> attackResultProcessor.apply(action))
			.flatMap(actionRepository::save)
			.map(TacticalActionMissileAttack.class::cast);
	}

}
