package org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.action.execution.StaticManeuverExecution;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverResult;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionStaticManeuver;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.table.maneuver.StaticManeuverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class StaticManeuverExecutionService {

	@Autowired
	private TacticalCharacterRepository characterRepository;

	@Autowired
	private StaticManeuverService staticManeuverService;

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private StaticManeuversBonusProcessor bonusProcessor;

	public Mono<TacticalActionStaticManeuver> execute(TacticalActionStaticManeuver action, StaticManeuverExecution execution) {
		action.setDifficulty(execution.getDifficulty());
		int roll = execution.getRoll().getResult();
		Map<String, Integer> modifiers = execution.getModifiers();
		return characterRepository.findById(action.getSource())
			.flatMap(tc -> bonusProcessor.getBonusMap(action, tc, modifiers))
			.map(map -> {
				int totalBonus = map.values().stream().reduce(0, (a, b) -> a + b);
				int total = roll + totalBonus;
				StaticManeuverResult result = staticManeuverService.getResult(total);
				action.setResult(result);
				action.setState(TacticalActionState.RESOLVED);
				action.setModifiers(map);
				action.setRoll(execution.getRoll());
				return action;
			})
			.flatMap(actionRepository::save)
			.map(TacticalActionStaticManeuver.class::cast);
	}
}
