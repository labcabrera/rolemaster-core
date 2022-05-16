package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.ob;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.springframework.stereotype.Component;

@Component
public class OBDefensiveBonusProcessor implements OBProcessor {

	private static final List<Debuff> NO_DEFENSIVE_BONUS_DEBUFS = Arrays.asList(Debuff.SHOCK, Debuff.PRONE, Debuff.UNCONSCIOUS,
		Debuff.INSTANT_DEATH);

	@Override
	public void accept(AttackContext context) {
		context.getTargets().entrySet().stream().forEach(e -> {
			int bd = getBonusDefensive(e.getValue());
			context.getAction().getOffensiveBonusMap().get(e.getKey()).put(OffensiveBonusModifier.DEFENSIVE_BONUS, -bd);
		});
	}

	private int getBonusDefensive(TacticalCharacter target) {
		for (Debuff debuff : NO_DEFENSIVE_BONUS_DEBUFS) {
			if (target.getCombatStatus().getDebuffs().containsKey(debuff)) {
				return 0;
			}
		}
		return target.getDefensiveBonus();
	}
}
