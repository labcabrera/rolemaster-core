package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class MeleeAttackDefensiveBonusProcessor {

	private static final List<Debuff> CANT_PARRY_DEBUFS = Arrays.asList(Debuff.CANT_PARRY, Debuff.SHOCK, Debuff.PRONE, Debuff.UNCONSCIOUS);

	@Autowired
	private TacticalActionRepository actionRepository;

	public Mono<MeleeAttackContext> apply(MeleeAttackContext context) {
		if (context.getAction().isFlumbe()) {
			return Mono.just(context);
		}
		return Mono.just(context)
			.map(ctx -> {
				context.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.SHIELD, getShieldBonus(context));
				return ctx;
			})
			.zipWith(getParryBonus(context), (a, b) -> {
				a.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.PARRY, b);
				return a;
			});
	}

	private Mono<Integer> getParryBonus(MeleeAttackContext context) {
		if (!canParry(context)) {
			return Mono.just(0);
		}
		String roundId = context.getAction().getRoundId();
		String sourceId = context.getAction().getSource();
		String targetId = context.getAction().getTarget();
		return actionRepository.findByRoundIdAndSource(roundId, targetId)
			.collectList()
			.map(list -> {
				return list.stream().filter(e -> e instanceof TacticalActionMeleeAttack)
					.map(e -> (TacticalActionMeleeAttack) e)
					.filter(e -> e.getParry() > 0 && e.getParried() == false)
					.filter(e -> e.getTarget() == null || sourceId.equals(e.getTarget()))
					.findFirst();
			})
			.flatMap(optional -> {
				if (optional.isEmpty()) {
					return Mono.just(0);
				}
				else {
					TacticalActionMeleeAttack entity = optional.get();
					Integer parry = entity.getParry();
					return actionRepository.save(entity).then(Mono.just(-parry));
				}
			});
	}

	private int getShieldBonus(MeleeAttackContext context) {
		return 0;
	}

	private boolean canParry(MeleeAttackContext context) {
		for (Debuff debuff : CANT_PARRY_DEBUFS) {
			if (context.getTarget().getCombatStatus().getDebuffs().containsKey(debuff)) {
				return false;
			}
		}
		return true;
	}

}
