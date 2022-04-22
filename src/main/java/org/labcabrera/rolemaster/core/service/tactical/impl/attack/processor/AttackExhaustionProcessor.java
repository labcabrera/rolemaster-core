package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

/**
 * Service that updates character data based on the outcome of an attack
 */
@Component
public class AttackExhaustionProcessor {

	private BigDecimal MELEE_DIVISOR = new BigDecimal("2");
	private BigDecimal MISSILE_DIVISOR = new BigDecimal("6");

	@Autowired
	private TacticalRoundRepository roundRepository;

	@Autowired
	private TacticalSessionRepository sessionRepository;

	public <T extends AttackContext<?>> Mono<T> apply(T context) {
		return roundRepository.findById(context.getAction().getRoundId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Round not found")))
			.flatMap(round -> this.apply(context, round));
	}

	private <T extends AttackContext<?>> Mono<T> apply(T context, TacticalRound round) {
		return sessionRepository.findById(round.getTacticalSessionId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Session not found")))
			.flatMap(session -> this.apply(context, session));
	}

	private <T extends AttackContext<?>> Mono<T> apply(T context, TacticalSession session) {
		BigDecimal base = BigDecimal.ONE;
		BigDecimal divisor = context.isMeleeAttack() ? MELEE_DIVISOR : MISSILE_DIVISOR;

		BigDecimal multiplierCustom = session.getExhaustionMultiplier();
		BigDecimal multiplierTemperature = new BigDecimal(session.getTemperature().getMultiplier());
		BigDecimal multiplierTerrain = new BigDecimal(session.getTerrain().getMultiplier());
		BigDecimal multiplierHp = multiplierHp(context.getSource());

		BigDecimal totalMultiplier = BigDecimal.ONE;
		totalMultiplier = totalMultiplier.add(multiplierCustom.subtract(BigDecimal.ONE));
		totalMultiplier = totalMultiplier.add(multiplierTemperature.subtract(BigDecimal.ONE));
		totalMultiplier = totalMultiplier.add(multiplierTerrain.subtract(BigDecimal.ONE));
		totalMultiplier = totalMultiplier.add(multiplierHp.subtract(BigDecimal.ONE));

		BigDecimal exhaustionPoints = base
			.multiply(totalMultiplier)
			.divide(divisor)
			.setScale(3, RoundingMode.HALF_EVEN);
		context.getAction().setExhaustionPoints(exhaustionPoints);
		return Mono.just(context);
	}

	private BigDecimal multiplierHp(TacticalCharacter tc) {
		int percent = tc.getHp().getPercent();
		if (percent < 50) {
			return new BigDecimal("4");
		}
		else if (percent < 75) {
			return new BigDecimal("2");
		}
		return BigDecimal.ONE;
	}

}