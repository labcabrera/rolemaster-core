package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.OffensiveBonusProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile.MissileAttackContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile.MissileAttackDefensiveProcessor;
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
	private TacticalCharacterRepository characterRepository;

	@Autowired
	private OffensiveBonusProcessor offensiveBonusProcessor;

	@Autowired
	private MissileAttackDefensiveProcessor missileAttackDefensiveProcessor;

	@Autowired
	private MissileAttackWeaponTableProcessor missileAttackWeaponTableProcessor;

	@Autowired
	private MissileAttackResultProcessor missileAttackResultProcessor;

	public Mono<TacticalActionMissileAttack> execute(TacticalActionMissileAttack action, MissileAttackExecution execution) {
		action.setDistance(execution.getDistance());
		action.setCover(execution.getCover());

		MissileAttackContext context = new MissileAttackContext();
		context.setAction(action);

		return Mono.just(context)
			.zipWith(characterRepository.findById(context.getAction().getSource()), (a, b) -> a.<MissileAttackContext>setSource(b))
			.zipWith(characterRepository.findById(context.getAction().getTarget()), (a, b) -> a.<MissileAttackContext>setTarget(b))
			.flatMap(offensiveBonusProcessor::apply)
			.flatMap(missileAttackDefensiveProcessor)
			.flatMap(missileAttackWeaponTableProcessor)
			.flatMap(missileAttackResultProcessor)
			.map(ctx -> ctx.getAction())
			.flatMap(actionRepository::save);
	}

}
