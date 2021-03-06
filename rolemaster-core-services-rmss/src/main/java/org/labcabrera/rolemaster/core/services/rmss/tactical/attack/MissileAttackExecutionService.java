package org.labcabrera.rolemaster.core.services.rmss.tactical.attack;

import java.util.EnumMap;

import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.services.commons.context.AttackContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MissileAttackExecutionService {

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private AttackProcessorService processorService;

	@Autowired
	private AttackContextLoader contextLoader;

	public Mono<TacticalActionMissileAttack> execute(JwtAuthenticationToken auth, TacticalActionMissileAttack action,
		MissileAttackExecution execution) {
		loadActionData(action, execution);
		return contextLoader.apply(auth, action)
			.flatMap(processorService::apply)
			.map(AttackContext::getAction)
			.flatMap(actionRepository::save)
			.map(TacticalActionMissileAttack.class::cast);
	}

	private void loadActionData(TacticalActionMissileAttack action, MissileAttackExecution execution) {
		action.setDistance(execution.getDistance());
		action.setCover(execution.getCover());
		action.setRolls(new EnumMap<>(AttackTargetType.class));
		action.getRolls().put(AttackTargetType.MAIN_HAND, execution.getRoll());
		action.setPreparationRounds(execution.getPreparationRounds());
		action.setCustomBonus(execution.getCustomBonus());
	}

}
