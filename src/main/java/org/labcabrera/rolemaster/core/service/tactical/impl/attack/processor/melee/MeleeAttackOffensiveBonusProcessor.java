package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee;

import org.labcabrera.rolemaster.core.model.character.inventory.CharacterWeapon;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.service.tactical.TacticalSkillService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.TacticalAttackContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.OffensiveExhaustionBonusCalculator;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.OffensiveHpBonusCalculator;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.OffensivePositionBonusCalculator;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.OffensiveTargetStatusBonusCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class MeleeAttackOffensiveBonusProcessor {

	@Autowired
	private TacticalSkillService skillService;

	@Autowired
	private OffensiveHpBonusCalculator offensiveHpBonusCalculator;

	@Autowired
	private OffensiveExhaustionBonusCalculator offensiveExhaustionBonusCalculator;

	@Autowired
	private OffensiveTargetStatusBonusCalculator offensiveTargetStatusBonusCalculator;

	@Autowired
	private OffensivePositionBonusCalculator offensivePositionBonusCalculator;

	public Mono<TacticalAttackContext> apply(TacticalAttackContext context) {
		if (context.getAction().isFlumbe()) {
			return Mono.just(context);
		}
		return Mono
			.just(context)
			.map(this::loadBonus)
			.flatMap(this::loadSkillBonus);
	}

	private TacticalAttackContext loadBonus(TacticalAttackContext context) {
		int flankBonus = offensivePositionBonusCalculator.getFlankBonus(context.getExecution().getPosition());
		int hpBonus = offensiveHpBonusCalculator.getBonus(context.getSource());
		int exhaustionBonus = offensiveExhaustionBonusCalculator.getBonus(context.getSource());
		int targetStatus = offensiveTargetStatusBonusCalculator.getBonus(context.getTarget());
		context.getAction().getOffensiveBonusMap().put("flank", flankBonus);
		context.getAction().getOffensiveBonusMap().put("hp", hpBonus);
		context.getAction().getOffensiveBonusMap().put("exhaustion", exhaustionBonus);
		context.getAction().getOffensiveBonusMap().put("targetStatus", targetStatus);
		return context;
	}

	private Mono<TacticalAttackContext> loadSkillBonus(TacticalAttackContext context) {
		TacticalCharacter source = context.getSource();
		CharacterWeapon weapon = source.getInventory().getMainHandWeapon();
		String skillId = weapon.getSkillId();
		return Mono.just(context)
			.zipWith(skillService.getSkill(source, skillId), (a, b) -> {
				a.getAction().getOffensiveBonusMap().put("skill", b);
				return a;
			});
	}

}
