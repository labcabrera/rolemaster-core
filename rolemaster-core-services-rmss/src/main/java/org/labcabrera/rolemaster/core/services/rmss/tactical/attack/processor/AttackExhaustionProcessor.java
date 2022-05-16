package org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.services.tactical.attack.processor.AbstractAttackProcessor;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

/**
 * Service that updates character data based on the outcome of an attack
 */
@Component
public class AttackExhaustionProcessor implements AbstractAttackProcessor {

	private static final BigDecimal MELEE_DIVISOR = new BigDecimal("2");
	private static final BigDecimal MISSILE_DIVISOR = new BigDecimal("6");

	public Mono<AttackContext> apply(AttackContext context) {
		TacticalSession session = context.getTacticalSession();
		boolean isMeleeAttack = context.getAction() instanceof TacticalActionMeleeAttack;
		BigDecimal base = BigDecimal.ONE;
		BigDecimal divisor = isMeleeAttack ? MELEE_DIVISOR : MISSILE_DIVISOR;

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
			.divide(divisor, 3, RoundingMode.HALF_EVEN);
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
