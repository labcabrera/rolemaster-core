package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import java.util.function.UnaryOperator;

import org.springframework.stereotype.Component;

@Component
public class MeleeAttackDefensiveBonusProcessor implements UnaryOperator<MeleeAttackContext> {

	@Override
	public MeleeAttackContext apply(MeleeAttackContext context) {
		return context;
	}

}
