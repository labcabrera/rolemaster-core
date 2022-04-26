package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.math.BigDecimal;
import java.util.List;

import org.labcabrera.rolemaster.core.model.combat.Bleeding;
import org.labcabrera.rolemaster.core.model.tactical.CombatStatus;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalCriticalResult;
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
public class AttackResultProcessor extends AbstractAttackProcessor {

	@Autowired
	private TacticalActionRepository tacticalActionRepository;

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	@Autowired
	private TacticalLogService logService;

	@Override
	public Mono<AttackContext> apply(AttackContext context) {
		TacticalActionAttack attack = context.getAction();
		log.info("Processing attack result for action {} ({})", attack.getId(), attack.getState());
		if (attack.getState() != TacticalActionState.PENDING_RESOLUTION) {
			return Mono.just(context);
		}
		return Mono.just(attack)
			.then(updateTarget(attack, AttackTargetType.MAIN_HAND))
			.then(updateTarget(attack, AttackTargetType.OFF_HAND))
			.then(updateSource(attack))
			.then(updateAttack(attack))
			.flatMap(logService::logAttackResult)
			.thenReturn(context);
	}

	private Mono<TacticalCharacter> updateTarget(TacticalActionAttack attack, AttackTargetType type) {
		if (!attack.getTargets().containsKey(type)) {
			return Mono.empty();
		}
		String characterId = attack.getTargets().get(type);
		return tacticalCharacterRepository.findById(characterId)
			.map(tc -> {
				int hp = attack.getAttackResults().get(type).getHp();
				tc.getHp().subtract(hp);
				return applyTargetCriticalResults(tc, attack.getCriticalResults().get(type));
			})
			.flatMap(tacticalCharacterRepository::save);
	}

	private Mono<TacticalCharacter> updateSource(TacticalActionAttack attack) {
		return tacticalCharacterRepository.findById(attack.getSource())
			.map(tc -> {
				if (attack.getExhaustionPoints() != null) {
					tc.getExhaustionPoints().substract(attack.getExhaustionPoints());
				}
				attack.getCriticalResults().values().stream().forEach(list -> applySourceCriticalResults(tc, list));
				if (attack.getExhaustionPoints() != null) {
					BigDecimal ep = tc.getExhaustionPoints().getCurrent().subtract(attack.getExhaustionPoints());
					tc.getExhaustionPoints().setCurrent(ep);
				}
				return tc;
			})
			.flatMap(tacticalCharacterRepository::save);
	}

	private TacticalCharacter applyTargetCriticalResults(TacticalCharacter target, List<TacticalCriticalResult> list) {
		if (list == null) {
			return target;
		}
		list.stream().map(TacticalCriticalResult::getCriticalTableResult).forEach(ctr -> {
			CombatStatus cs = target.getCombatStatus();
			if (ctr.getHp() != null) {
				target.getHp().subtract(ctr.getHp());
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
			target.getCombatStatus().getInjuries().putAll(ctr.getInjuries());
		});
		return target;
	}

	private TacticalCharacter applySourceCriticalResults(TacticalCharacter source, List<TacticalCriticalResult> list) {
		if (list == null) {
			return source;
		}
		CombatStatus cs = source.getCombatStatus();
		list.stream().map(TacticalCriticalResult::getCriticalTableResult).forEach(ctr -> {
			ctr.getBuffs().entrySet().stream().forEach(e -> cs.addBuff(e.getKey(), e.getValue()));
			if (ctr.getBonus() != null) {
				cs.addBonus(ctr.getBonus().getBonus(), ctr.getBonus().getRounds());
			}
		});
		return source;
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
