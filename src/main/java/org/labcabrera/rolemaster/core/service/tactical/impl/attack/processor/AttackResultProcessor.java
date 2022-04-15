package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.model.combat.Bleeding;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.tactical.CombatStatus;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackResult;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Service that updates character data based on the outcome of an attack
 */
@Component
@Slf4j
public class AttackResultProcessor {

	@Autowired
	private TacticalActionRepository tacticalActionRepository;

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	public Mono<TacticalActionAttack> apply(TacticalActionAttack attack) {
		log.info("Processing attack result for action {} ({})", attack.getId(), attack.getState());
		if (attack.isFlumbe()) {
			attack.setState(TacticalActionState.PENDING_FUMBLE_RESOLUTION);
			return Mono.just(attack);
		}
		if (attack.hasPendingCriticalResolution()) {
			attack.setState(TacticalActionState.PENDING_CRITICAL_RESOLUTION);
			return Mono.just(attack);
		}
		return Mono.just(attack)
			.flatMap(this::updateTarget)
			.map(e -> attack)
			.flatMap(this::updateSource)
			.map(e -> attack)
			.flatMap(this::updateAttack);
	}

	private Mono<TacticalCharacter> updateTarget(TacticalActionAttack attack) {
		return updateTarget(attack, attack.getAttackResult());
	}

	private Mono<TacticalCharacter> updateTarget(TacticalActionAttack attack, AttackResult attackResult) {
		return tacticalCharacterRepository.findById(attack.getTarget())
			.map(tc -> {
				if (attackResult.getHpResult() != null) {
					tc.getHp().subtract(attackResult.getHpResult());
				}
				if (attackResult.getCriticalResult() != null && attackResult.getCriticalResult().getCriticalTableResult() != null) {
					CriticalTableResult ctr = attackResult.getCriticalResult().getCriticalTableResult();
					CombatStatus cs = tc.getCombatStatus();

					if (ctr.getHp() != null) {
						tc.getHp().subtract(ctr.getHp());
					}
					if (ctr.getBleeding() != null) {
						Bleeding bleeding = Bleeding.builder()
							.hp(ctr.getBleeding())
							.description("Bleeding for critical: " + ctr.getText())
							.build();
						cs.getBleding().add(bleeding);
					}
					for (Entry<Debuff, Integer> dse : ctr.getDebuffs().entrySet()) {
						tc.getCombatStatus().addDebuff(dse.getKey(), dse.getValue());
					}
					tc.getCombatStatus().getOtherEfects().addAll(ctr.getOtherEffects());
				}
				return tc;
			})
			.flatMap(tacticalCharacterRepository::save);
	}

	private Mono<TacticalCharacter> updateSource(TacticalActionAttack attack) {
		return updateSource(attack, attack.getAttackResult());
	}

	private Mono<TacticalCharacter> updateSource(TacticalActionAttack attack, AttackResult attackResult) {
		return tacticalCharacterRepository.findById(attack.getTarget())
			.map(tc -> {
				if (attack.getExhaustionPoints() != null) {
					tc.getExhaustionPoints().substract(attack.getExhaustionPoints());
				}
				if (attackResult.getCriticalResult() != null && attackResult.getCriticalResult().getCriticalTableResult() != null) {
					CriticalTableResult ctr = attackResult.getCriticalResult().getCriticalTableResult();
					if (ctr.getBonus() != null) {
						tc.getCombatStatus().getBonus().add(ctr.getBonus());
					}
				}
				return tc;
			})
			.flatMap(tacticalCharacterRepository::save);
	}

	private Mono<TacticalActionAttack> updateAttack(TacticalActionAttack attack) {
		log.info("Updating attack result for action {} ({})", attack.getId(), attack.getState());
		return Mono.just(attack)
			.map(e -> {
				e.setState(TacticalActionState.RESOLVED);
				return e;
			})
			.flatMap(tacticalActionRepository::save)
			.map(e -> (TacticalActionAttack) e);
	}

}
