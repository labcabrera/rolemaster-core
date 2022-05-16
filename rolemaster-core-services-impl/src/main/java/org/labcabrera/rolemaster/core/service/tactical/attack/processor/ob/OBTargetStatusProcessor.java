package org.labcabrera.rolemaster.core.service.tactical.attack.processor.ob;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.springframework.stereotype.Component;

@Component
public class OBTargetStatusProcessor implements OBProcessor {

	@Override
	public void accept(AttackContext context) {
		context.getTargets().entrySet().stream().forEach(e -> {
			int bonus = getBonusTargetStatus(e.getValue());
			context.getAction().getOffensiveBonusMap().get(e.getKey()).put(OffensiveBonusModifier.TARGET_STATUS, bonus);
		});
	}

	private int getBonusTargetStatus(TacticalCharacter target) {
		int result = 0;
		if (target.getCombatStatus().getDebuffs().containsKey(Debuff.PRONE)) {
			result = 50;
		}
		else if (target.getCombatStatus().getDebuffs().containsKey(Debuff.UNCONSCIOUS)) {
			result = 50;
		}
		else if (target.getCombatStatus().getDebuffs().containsKey(Debuff.DOWNED)) {
			result = 30;
		}
		else if (target.getCombatStatus().getDebuffs().containsKey(Debuff.STUNNED)) {
			result = 20;
		}
		if (target.getCombatStatus().getDebuffs().containsKey(Debuff.SURPRISED)) {
			result += 20;
		}
		return result;
	}
}
