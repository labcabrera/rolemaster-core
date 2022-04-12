package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile.MissileAttackContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile.MissileAttackDefensiveProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile.MissileAttackOffensiveProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile.MissileAttackResultProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile.MissileAttackWeaponTableProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MissileAttackExecutionService {

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private MissileAttackOffensiveProcessor missileAttackOffensiveProcessor;

	@Autowired
	private MissileAttackDefensiveProcessor missileAttackDefensiveProcessor;

	@Autowired
	private MissileAttackWeaponTableProcessor missileAttackWeaponTableProcessor;

	@Autowired
	private MissileAttackResultProcessor missileAttackResultProcessor;

	public Mono<TacticalActionMissileAttack> execute(TacticalActionMissileAttack action, MissileAttackExecution execution) {
		MissileAttackContext context = MissileAttackContext.builder()
			.action(action)
			.execution(execution)
			.build();
		return Mono.just(context)
			.flatMap(missileAttackOffensiveProcessor)
			.flatMap(missileAttackDefensiveProcessor)
			.flatMap(missileAttackWeaponTableProcessor)
			.flatMap(missileAttackResultProcessor)
			.map(ctx -> ctx.getAction())
			.flatMap(actionRepository::save);
	}

}
