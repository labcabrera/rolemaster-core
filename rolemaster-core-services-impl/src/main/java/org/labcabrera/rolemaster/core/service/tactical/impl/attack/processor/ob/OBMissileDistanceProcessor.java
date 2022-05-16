package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.ob;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OBMissileDistanceProcessor implements OBProcessor {

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	@Autowired
	private TacticalCharacterItemService itemService;

	public void accept(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMissileAttack missileAttack) {
			CharacterItem itemMainHand = itemResolver.getMainHandWeapon(context.getSource());
			Float distance = missileAttack.getDistance();
			int bonus = itemService.getRangeModifier(itemMainHand, distance, context);
			Map<AttackTargetType, Map<OffensiveBonusModifier, Integer>> map = context.getAction().getOffensiveBonusMap();
			map.get(AttackTargetType.MAIN_HAND).put(OffensiveBonusModifier.DISTANCE, bonus);
		}
	}
}
