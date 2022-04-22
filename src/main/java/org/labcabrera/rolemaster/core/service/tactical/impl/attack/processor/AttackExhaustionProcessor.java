package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

/**
 * Service that updates character data based on the outcome of an attack
 */
@Component
public class AttackExhaustionProcessor {

	private BigDecimal MELEE_DIVISOR = new BigDecimal("2");
	private BigDecimal MISSILE_DIVISOR = new BigDecimal("6");

	public <T extends AttackContext<?>> Mono<T> apply(T context) {
		BigDecimal base = BigDecimal.ONE;
		BigDecimal divisor = context.isMeleeAttack() ? MELEE_DIVISOR : MISSILE_DIVISOR;

		BigDecimal multiplierCustom = context.getSession().getExhaustionMultiplier();
		BigDecimal multiplierTemperature = new BigDecimal(context.getSession().getTemperature().getMultiplier());
		BigDecimal multiplierTerrain = new BigDecimal(context.getSession().getTerrain().getMultiplier());
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
