package org.labcabrera.rolemaster.core.service.tactical.attack.processor.ob;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.service.tactical.TacticalCharacterItemResolver;
import org.labcabrera.rolemaster.core.service.tactical.attack.processor.MissilePreparationServiceBonusProcessor;
import org.labcabrera.rolemaster.core.service.tactical.attack.processor.OBProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OBMissilePreparationProcessor implements OBProcessor {

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	@Autowired
	private MissilePreparationServiceBonusProcessor missilePreparationServiceBonusProcessor;

	@Override
	public void accept(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMissileAttack missileAttack) {
			CharacterItem item = itemResolver.getMainHandWeapon(context.getSource());
			int rounds = missileAttack.getPreparationRounds();
			int value = -missilePreparationServiceBonusProcessor.getPreparationBonus(item.getItemId(), rounds);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND)
				.put(OffensiveBonusModifier.MISSILE_PREPARATION_ROUNDS, value);
		}
	}
}
