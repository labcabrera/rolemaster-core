package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.labcabrera.rolemaster.core.model.tactical.CombatStatus;
import org.labcabrera.rolemaster.core.model.tactical.DebufStatus;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.TacticalAttackContext;
import org.springframework.stereotype.Component;

@Component
public class MeleeAttackDefensiveBonusProcessor implements UnaryOperator<TacticalAttackContext> {

	@Override
	public TacticalAttackContext apply(TacticalAttackContext context) {
		if (context.getAction().getFumbleResult() != null) {
			return context;
		}
		int defensiveBonus = getDefensiveBonus(context);
		int parryBonus = getParryBonus(context);
		int shieldBonus = getShieldBonus(context);

		context.getAction().getDefensiveBonusMap().put("defensiveBonus", defensiveBonus);
		context.getAction().getDefensiveBonusMap().put("parryBonus", parryBonus);
		context.getAction().getDefensiveBonusMap().put("shieldBonus", shieldBonus);

		return context;
	}

	private int getDefensiveBonus(TacticalAttackContext context) {
		return context.getTarget().getBaseDefensiveBonus();
	}

	private int getParryBonus(TacticalAttackContext context) {
		if (!canParry(context)) {
			return 0;
		}
		String source = context.getAction().getSource();
		String target = context.getAction().getTarget();

		// We look for any attack action directed against the attacker
		List<TacticalActionMeleeAttack> checkList = context.getActions().stream()
			.filter(e -> e.getSource().equals(target))
			.filter(e -> e instanceof TacticalActionMeleeAttack)
			.map(e -> (TacticalActionMeleeAttack) e)
			.filter(e -> e.getParry() > 0)
			.filter(e -> e.getTarget().equals(source) || e.getTarget() == null)
			.collect(Collectors.toList());

		if (!checkList.isEmpty()) {
			//TODO prevent multiple parries against diferent attackers
			return checkList.iterator().next().getParry();
		}
		return 0;
	}

	private int getShieldBonus(TacticalAttackContext context) {
		return 0;
	}

	private boolean canParry(TacticalAttackContext context) {
		CombatStatus cs = context.getTarget().getCombatStatus();
		if (cs.getDebufStatusMap().containsKey(DebufStatus.CANT_PARRY)) {
			return false;
		}
		else if (cs.getDebufStatusMap().containsKey(DebufStatus.PRONE)) {
			return false;
		}
		return true;

	}

}
