package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.ob;

import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.service.context.AttackContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.MissilePreparationServiceBonusProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OBMissilePreparationProcessor implements OBProcessor {

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	@Autowired
	private MissilePreparationServiceBonusProcessor missilePreparationServiceBonusProcessor;

	@Override
	public AttackContext process(AttackContext context) {
		if (context.getAction() instanceof TacticalActionMissileAttack missileAttack) {
			CharacterItem item = itemResolver.getMainHandWeapon(context.getSource());
			int rounds = missileAttack.getPreparationRounds();
			int value = -missilePreparationServiceBonusProcessor.getPreparationBonus(item.getItemId(), rounds);
			context.getAction().getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND)
				.put(OffensiveBonusModifier.MISSILE_PREPARATION_ROUNDS, value);
		}
		return context;
	}
}
