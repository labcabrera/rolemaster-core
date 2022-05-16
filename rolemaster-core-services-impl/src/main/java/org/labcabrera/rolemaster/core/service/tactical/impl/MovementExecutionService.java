package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.labcabrera.rolemaster.core.dto.action.execution.MovementExecution;
import org.labcabrera.rolemaster.core.model.combat.Penalty;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;
import org.labcabrera.rolemaster.core.model.maneuver.MovingManeuverResult;
import org.labcabrera.rolemaster.core.model.tactical.CombatStatus;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovement;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.service.context.loader.TacticalActionContextLoader;
import org.labcabrera.rolemaster.core.service.table.maneuver.MovingManeuverService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalSkillService;
import org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers.MovingManeuverBonusService;
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
	private MovingManeuverBonusService movingManeuverBonusService;

	@Autowired
	private MovingManeuverService movingManeuverService;

	@Autowired
	private TacticalSkillService tacticalSkillService;

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	public Mono<TacticalAction> execute(TacticalActionMovement tacticalMovement, MovementExecution movementExecution) {
		load(tacticalMovement, movementExecution);
		return contextLoader.apply(tacticalMovement)
			.flatMap(this::loadSkill)
			.map(this::processManeuver)
			.map(this::processResult)
			.flatMap(this::processFailure)
			.map(TacticalActionContext::getAction)
			.flatMap(tacticalActionRepository::save);
	}

	private void load(TacticalActionMovement tacticalMovement, MovementExecution movementExecution) {
		tacticalMovement.setRoll(movementExecution.getRoll());
		tacticalMovement.setDifficulty(movementExecution.getDifficulty());
		tacticalMovement.setCombatSituation(movementExecution.getCombatSituation());
		if (movementExecution.getCustomBonus() != null && movementExecution.getCustomBonus() != 0) {
			tacticalMovement.getBonusMap().put("custom", movementExecution.getCustomBonus());
		}
	}

	private Mono<TacticalActionContext<TacticalActionMovement>> loadSkill(TacticalActionContext<TacticalActionMovement> context) {
		return tacticalSkillService.getSkill(context.getSource(), SPRINTING)
			.map(skillBonus -> {
				context.getAction().getBonusMap().put("skill", skillBonus);
				return context;
			});
	}

	private TacticalActionContext<TacticalActionMovement> processManeuver(TacticalActionContext<TacticalActionMovement> context) {
		TacticalActionMovement movement = context.getAction();
		ManeuverDifficulty difficulty = movement.getDifficulty();
		if (difficulty == ManeuverDifficulty.NONE) {
			movement.setManeuverResult(MovingManeuverResult.builder().result(100).build());
		}
		else {
			movingManeuverBonusService.loadBonus(context, movement.getBonusMap());
			int totalBonus = movement.getBonusMap().values().stream().reduce(0, (a, b) -> a + b);
			int roll = movement.getRoll();
			int result = totalBonus + roll;
			MovingManeuverResult movingManeuverResult = movingManeuverService.getResult(difficulty, result);
			movement.setManeuverResult(movingManeuverResult);
		}
		return context;
	}

	private TacticalActionContext<TacticalActionMovement> processResult(TacticalActionContext<TacticalActionMovement> context) {
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
		BigDecimal scaled = result.multiply(BigDecimal.valueOf(scale)).setScale(1, RoundingMode.HALF_EVEN);
		movement.setDistance(result.doubleValue());
		movement.setDistanceScaled(scaled.doubleValue());
		movement.setState(TacticalActionState.RESOLVED);
		return context;
	}

	private Mono<TacticalActionContext<TacticalActionMovement>> processFailure(TacticalActionContext<TacticalActionMovement> context) {
		boolean update = false;
		MovingManeuverResult maneuverResult = context.getAction().getManeuverResult();
		TacticalCharacter source = context.getSource();
		CombatStatus combatStatus = source.getCombatStatus();
		if (maneuverResult.getHp() != null) {
			source.getHp().add(maneuverResult.getHp());
			update = true;
		}
		if (maneuverResult.getBleeding() != null) {
			combatStatus.getBleeding().add(maneuverResult.getBleeding());
			update = true;
		}
		if (!maneuverResult.getDebuffs().isEmpty()) {
			maneuverResult.getDebuffs().entrySet().stream().forEach(entry -> combatStatus.addDebuff(entry.getKey(), entry.getValue() + 1));
			update = true;
		}
		if (!maneuverResult.getInjuries().isEmpty()) {
			combatStatus.getInjuries().putAll(maneuverResult.getInjuries());
			update = true;
		}
		if (maneuverResult.getPenalty() != null) {
			Penalty penalty = Penalty.builder()
				.penalty(maneuverResult.getPenalty().getPenalty())
				.rounds(maneuverResult.getPenalty().getRounds())
				.build();
			if (penalty.getRounds() != null) {
				penalty.setRounds(1 + penalty.getRounds());
			}
			combatStatus.getPenalties().add(penalty);
		}
		if (!update) {
			return Mono.just(context);
		}
		else {
			return tacticalCharacterRepository.save(source).thenReturn(context);
		}
	}

}
