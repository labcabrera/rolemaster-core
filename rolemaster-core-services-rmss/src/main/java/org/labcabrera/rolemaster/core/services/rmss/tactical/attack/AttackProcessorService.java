package org.labcabrera.rolemaster.core.services.rmss.tactical.attack;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.AttackBreakageProcessor;
import org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.AttackExhaustionProcessor;
import org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.AttackFumbleProcessor;
import org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.AttackResultProcessor;
import org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.AttackWeaponTableProcessor;
import org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.MeleeAttackDefensiveBonusProcessor;
import org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor.OffensiveBonusProcessor;
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
	private AttackExhaustionProcessor exhaustionProcessor;

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
			.flatMap(exhaustionProcessor::apply)
			.flatMap(attackResultProcessor::apply);
	}

}
