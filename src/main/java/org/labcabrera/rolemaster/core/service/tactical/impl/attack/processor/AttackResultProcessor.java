package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.model.combat.Bleeding;
import org.labcabrera.rolemaster.core.model.tactical.CombatStatus;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalLogService;
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

	@Autowired
	private TacticalLogService logService;

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
			.then(updateTarget(attack, AttackTargetType.MAIN_HAND))
			.then(updateTarget(attack, AttackTargetType.OFF_HAND))
			.then(updateSource(attack))
			.then(updateAttack(attack))
			.flatMap(logService::logAttackResult);
	}

	private Mono<TacticalCharacter> updateTarget(TacticalActionAttack attack, AttackTargetType type) {
		if (!attack.getTargets().containsKey(type)) {
			return Mono.empty();
		}
		String characterId = attack.getTargets().get(type);
		return tacticalCharacterRepository.findById(characterId)
			.map(tc -> {
				attack.getAttackResults().stream().forEach(ar -> tc.getHp().subtract(ar.getHp()));
				attack.getCriticalResults().stream().map(e -> e.getCriticalTableResult()).forEach(ctr -> {
					CombatStatus cs = tc.getCombatStatus();
					if (ctr.getHp() != null) {
						tc.getHp().subtract(ctr.getHp());
					}
					if (ctr.getBleeding() != null) {
						cs.getBleeding().add(Bleeding.builder()
							.hp(ctr.getBleeding())
							.build());
					}
					if (ctr.getPenalty() != null) {
						cs.getPenalties().add(ctr.getPenalty());
					}
					ctr.getDebuffs().entrySet().stream().forEach(e -> cs.addDebuff(e.getKey(), e.getValue()));
					ctr.getInjuries().entrySet().stream().forEach(e -> cs.getInjuries().put(e.getKey(), e.getValue()));
					tc.getCombatStatus().getInjuries().putAll(ctr.getInjuries());
				});
				return tc;
			})
			.flatMap(tacticalCharacterRepository::save);
	}

	private Mono<TacticalCharacter> updateSource(TacticalActionAttack attack) {
		return tacticalCharacterRepository.findById(attack.getSource())
			.map(tc -> {
				if (attack.getExhaustionPoints() != null) {
					tc.getExhaustionPoints().substract(attack.getExhaustionPoints());
				}
				attack.getCriticalResults().stream().map(e -> e.getCriticalTableResult()).forEach(ctr -> {
					CombatStatus cs = tc.getCombatStatus();
					ctr.getBuffs().entrySet().stream().forEach(e -> cs.addBuff(e.getKey(), e.getValue()));
					if (ctr.getBonus() != null) {
						cs.addBonus(ctr.getBonus().getBonus(), ctr.getBonus().getRounds());
					}
				});
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
			.flatMap(tacticalActionRepository::save);
	}

}
