package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.model.combat.Bleeding;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.tactical.CombatStatus;
import org.labcabrera.rolemaster.core.model.tactical.DebufStatus;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

/**
 * Service that updates character data based on the outcome of an attack
 */
@Component
public class AttackResultProcessor {

	@Autowired
	private TacticalActionRepository tacticalActionRepository;

	@Autowired
	private TacticalCharacterContextRepository tacticalCharacterRepository;

	public Mono<TacticalActionAttack> apply(TacticalActionAttack attack) {
		if (attack.getState() != TacticalActionState.PENDING_RESOLUTION) {
			Mono.just(attack);
		}
		return Mono.just(attack)
			.flatMap(this::updateTarget)
			.map(e -> attack)
			.flatMap(this::updateSource)
			.map(e -> attack)
			.flatMap(this::updateAttack);
	}

	private Mono<TacticalCharacter> updateTarget(TacticalActionAttack attack) {
		return tacticalCharacterRepository.findById(attack.getTarget())
			.map(tc -> {
				if (attack.getHpResult() != null) {
					tc.getHp().subtract(attack.getHpResult());
				}
				if (attack.getCriticalResult() != null && attack.getCriticalResult().getCriticalTableResult() != null) {
					CriticalTableResult ctr = attack.getCriticalResult().getCriticalTableResult();
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
					for (Entry<DebufStatus, Integer> dse : ctr.getDebufMap().entrySet()) {
						tc.getCombatStatus().addDebufStatus(dse.getKey(), dse.getValue());
					}
					tc.getCombatStatus().getOtherEfects().addAll(ctr.getOtherEfects());
				}
				return tc;
			})
			.flatMap(tacticalCharacterRepository::save);
	}

	private Mono<TacticalCharacter> updateSource(TacticalActionAttack attack) {
		return tacticalCharacterRepository.findById(attack.getTarget())
			.map(tc -> {
				if (attack.getExhaustionPoints() != null) {
					tc.getExhaustionPoints().substract(attack.getExhaustionPoints());
				}
				if (attack.getCriticalResult() != null && attack.getCriticalResult().getCriticalTableResult() != null) {
					CriticalTableResult ctr = attack.getCriticalResult().getCriticalTableResult();
					if (ctr.getBonus() != null) {
						tc.getCombatStatus().getBonus().add(ctr.getBonus());
					}
				}
				return tc;
			})
			.flatMap(tacticalCharacterRepository::save);
	}

	private Mono<TacticalActionAttack> updateAttack(TacticalActionAttack attack) {
		return tacticalActionRepository.findById(attack.getId())
			.map(e -> {
				e.setState(TacticalActionState.RESOLVED);
				return e;
			})
			.flatMap(tacticalActionRepository::save)
			.map(e -> (TacticalActionAttack) e);
	}

}
