package org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.action.execution.MovingManeuverExecution;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;
import org.labcabrera.rolemaster.core.model.maneuver.MovingManeuverResult;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovingManeuver;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.table.maneuver.MovingManeuverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MovingManeuverExecutionService {

	@Autowired
	private TacticalCharacterRepository characterRepository;

	@Autowired
	private MovingManeuverService movingManeuverService;

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private MovingManeuversBonusProcessor bonusProcessor;

	public Mono<TacticalActionMovingManeuver> execute(TacticalActionMovingManeuver action, MovingManeuverExecution execution) {
		ManeuverDifficulty difficulty = execution.getDifficulty();
		int roll = execution.getRoll().getResult();
		Map<String, Integer> modifiers = execution.getModifiers();

		return characterRepository.findById(action.getSource())
			.flatMap(tc -> bonusProcessor.getBonusMap(action, tc, modifiers))
			.map(map -> {
				int totalBonus = map.values().stream().reduce(0, (a, b) -> a + b);
				int total = roll + totalBonus;
				MovingManeuverResult result = movingManeuverService.getResult(difficulty, total);

				action.setResult(result);
				action.setState(TacticalActionState.RESOLVED);
				action.setModifiers(map);
				action.setRoll(execution.getRoll());

				return action;
			})
			.flatMap(actionRepository::save)
			.map(TacticalActionMovingManeuver.class::cast);
	}
}
