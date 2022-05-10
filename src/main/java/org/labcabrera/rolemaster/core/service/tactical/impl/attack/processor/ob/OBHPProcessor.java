package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.ob;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.service.context.AttackContext;
import org.springframework.stereotype.Component;

/**
 * Hit points offensive bonus processor.
 */
@Component
public class OBHPProcessor implements OBProcessor {

	@Override
	public void accept(AttackContext context) {
		Map<AttackTargetType, Map<OffensiveBonusModifier, Integer>> map = context.getAction().getOffensiveBonusMap();
		map.get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.HP, getBonusHp(context.getSource()));
		map.get(AttackTargetType.OFF_HAND).put(OffensiveBonusModifier.HP, getBonusHp(context.getSource()));
	}

	private int getBonusHp(TacticalCharacter source) {
		Integer percent = source.getHp().getPercent();
		if (percent < 25) {
			return -30;
		}
		else if (percent < 50) {
			return -20;
		}
		else if (percent < 75) {
			return -10;
		}
		return 0;
	}

}
