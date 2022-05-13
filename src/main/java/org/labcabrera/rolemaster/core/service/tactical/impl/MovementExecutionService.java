package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.labcabrera.rolemaster.core.dto.action.execution.MovementExecution;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;
import org.labcabrera.rolemaster.core.model.maneuver.MovingManeuverResult;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovement;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.service.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.service.context.loader.TacticalActionContextLoader;
import org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers.bonus.ManeuverBonusService;
import org.labcabrera.rolemaster.core.table.maneuver.MovingManeuverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MovementExecutionService {

	private static final String SPRINTING = "sprinting";

	@Autowired
	private TacticalActionContextLoader contextLoader;

	@Autowired
	private TacticalActionRepository tacticalActionRepository;

	@Autowired
	private ManeuverBonusService maneuverBonusService;

	@Autowired
	private MovingManeuverService movingManeuverService;

	public Mono<TacticalAction> execute(TacticalActionMovement tacticalMovement, MovementExecution movementExecution) {
		load(tacticalMovement, movementExecution);
		return contextLoader.apply(tacticalMovement)
			.map(this::processManeuver)
			.map(this::processResult)
			.map(ctx -> ctx.getAction())
			.flatMap(tacticalActionRepository::save);
	}

	private void load(TacticalActionMovement tacticalMovement, MovementExecution movementExecution) {
		tacticalMovement.setRoll(movementExecution.getRoll());
		tacticalMovement.setDifficulty(movementExecution.getDifficulty());
		if (movementExecution.getCustomBonus() != null && movementExecution.getCustomBonus() != 0) {
			tacticalMovement.getBonusMap().put("custom", movementExecution.getCustomBonus());
		}
	}

	private TacticalActionContext<TacticalActionMovement> processManeuver(TacticalActionContext<TacticalActionMovement> context) {
		TacticalActionMovement movement = context.getAction();
		ManeuverDifficulty difficulty = movement.getDifficulty();
		if (difficulty == ManeuverDifficulty.NONE) {
			movement.setManeuverResult(MovingManeuverResult.builder().result(100).build());
		}
		else {
			maneuverBonusService.loadBonus(context, SPRINTING, movement.getBonusMap());
			int totalBonus = movement.getBonusMap().values().stream().reduce(0, (a, b) -> a + b);
			int roll = movement.getRoll();
			int result = totalBonus + roll;
			MovingManeuverResult movingManeuverResult = movingManeuverService.getResult(difficulty, result);
			movement.setManeuverResult(movingManeuverResult);
		}
		return context;
	}

	private TacticalActionContext<TacticalActionMovement> processResult(TacticalActionContext<TacticalActionMovement> context) {
		//TODO check !result

		TacticalActionMovement movement = context.getAction();
		int baseMovementRate = context.getSource().getBaseMovementRate();
		int percent = context.getAction().getActionPercent();
		double paceMultiplier = movement.getPace().getMultiplier();
		double scale = context.getTacticalSession().getScale();
		int maneuverResult = context.getAction().getManeuverResult().getResult();

		BigDecimal result = new BigDecimal(baseMovementRate)
			.multiply(new BigDecimal(maneuverResult))
			.multiply(new BigDecimal(percent))
			.multiply(BigDecimal.valueOf(paceMultiplier))
			.divide(new BigDecimal(10000), 1, RoundingMode.HALF_EVEN);
		BigDecimal scaled = result.multiply(new BigDecimal(scale)).setScale(1, RoundingMode.HALF_EVEN);
		movement.setDistance(result.doubleValue());
		movement.setDistanceScaled(scaled.doubleValue());
		movement.setState(TacticalActionState.RESOLVED);
		return context;
	}

}
