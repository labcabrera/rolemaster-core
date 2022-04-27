package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.labcabrera.rolemaster.core.service.context.AttackContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackBreakageProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackFumbleProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackResultProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackWeaponTableProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.MeleeAttackDefensiveBonusProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.OffensiveBonusProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class AttackProcessorService {

	@Autowired
	private OffensiveBonusProcessor offensiveBonusProcessor;

	@Autowired
	private MeleeAttackDefensiveBonusProcessor defensiveBonusProcessor;

	@Autowired
	private AttackWeaponTableProcessor weaponTableProcessor;

	@Autowired
	private AttackFumbleProcessor fumbleProcessor;

	@Autowired
	private AttackBreakageProcessor breakageProcessor;

	@Autowired
	private AttackResultProcessor attackResultProcessor;

	public Mono<AttackContext> apply(AttackContext context) {
		return Mono.just(context)
			.flatMap(fumbleProcessor::apply)
			.flatMap(breakageProcessor::apply)
			.flatMap(offensiveBonusProcessor::apply)
			.flatMap(defensiveBonusProcessor::apply)
			.flatMap(weaponTableProcessor::apply)
			.flatMap(attackResultProcessor::apply);
	}

}
