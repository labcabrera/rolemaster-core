package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee;

import org.labcabrera.rolemaster.core.model.tactical.CombatStatus;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.TacticalAttackContext;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class MeleeAttackDefensiveBonusProcessor {

	public Mono<TacticalAttackContext> apply(TacticalAttackContext context) {
		if (context.getAction().isFlumbe()) {
			return Mono.just(context);
		}
		return Mono.just(context)
			.map(ctx -> {
				int defensiveBonus = getDefensiveBonus(context);
				int shieldBonus = getShieldBonus(context);
				context.getAction().getDefensiveBonusMap().put("defensiveBonus", defensiveBonus);
				context.getAction().getDefensiveBonusMap().put("shieldBonus", shieldBonus);
				return ctx;
			})
			.zipWith(getParryBonus(context))
			.map(pair -> {
				TacticalAttackContext ctx = pair.getT1();
				int parryBonus = pair.getT2();
				context.getAction().getDefensiveBonusMap().put("parryBonus", parryBonus);
				return ctx;
			});
	}

	private int getDefensiveBonus(TacticalAttackContext context) {
		return context.getTarget().getBaseDefensiveBonus();
	}

	private Mono<Integer> getParryBonus(TacticalAttackContext context) {
		if (!canParry(context)) {
			return Mono.just(0);
		}
		//TODO read from actions
		return Mono.just(0);
	}

	private int getShieldBonus(TacticalAttackContext context) {
		return 0;
	}

	private boolean canParry(TacticalAttackContext context) {
		CombatStatus cs = context.getTarget().getCombatStatus();
		if (cs.getDebuffs().containsKey(Debuff.CANT_PARRY)) {
			return false;
		}
		else if (cs.getDebuffs().containsKey(Debuff.PRONE)) {
			return false;
		}
		return true;

	}

}
