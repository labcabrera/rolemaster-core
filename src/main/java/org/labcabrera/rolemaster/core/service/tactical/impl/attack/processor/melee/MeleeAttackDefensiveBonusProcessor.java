package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class MeleeAttackDefensiveBonusProcessor {

	//	private static final List<Debuff> CANT_PARRY_DEBUFS = Arrays.asList(Debuff.CANT_PARRY, Debuff.SHOCK, Debuff.PRONE, Debuff.UNCONSCIOUS);

	//	@Autowired
	//	private TacticalActionRepository actionRepository;

	public Mono<MeleeAttackContext> apply(MeleeAttackContext context) {
		if (context.getAction().isFlumbe()) {
			return Mono.just(context);
		}
		return Mono.just(context)
			.map(ctx -> {
				//context.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.SHIELD, getShieldBonus(context));
				return ctx;
			});
		//			.zipWith(getParryBonus(context), (a, b) -> {
		//				//a.getAction().getOffensiveBonusMap().put(OffensiveBonusModifier.PARRY, b);
		//				return a;
		//			});
	}

	//	private Mono<Integer> getParryBonus(MeleeAttackContext context, AttackTargetType type) {
	//		if (!canParry(context)) {
	//			return Mono.just(0);
	//		}
	//		String roundId = context.getAction().getRoundId();
	//		String sourceId = context.getAction().getSource();
	//		String targetId = context.getAction().getTarget();
	//		return actionRepository.findByRoundIdAndSource(roundId, targetId)
	//			.collectList()
	//			.map(list -> list.stream().filter(TacticalActionMeleeAttack.class::isInstance)
	//				.map(TacticalActionMeleeAttack.class::cast)
	//				.filter(e -> e.getParry() > 0 && !e.getParried())
	//				.filter(e -> e.getTarget() == null || sourceId.equals(e.getTarget()))
	//				.findFirst())
	//			.flatMap(optional -> {
	//				if (optional.isEmpty()) {
	//					return Mono.just(0);
	//				}
	//				else {
	//					TacticalActionMeleeAttack entity = optional.get();
	//					Integer parry = entity.getParry();
	//					return actionRepository.save(entity).then(Mono.just(-parry));
	//				}
	//			});
	//	}
	//
	//	private int getShieldBonus(MeleeAttackContext context) {
	//		//TODO shield bonus
	//		return 0;
	//	}
	//
	//	private boolean canParry(MeleeAttackContext context, TacticalCharacter tc) {
	//		for (Debuff debuff : CANT_PARRY_DEBUFS) {
	//			if (tc.getCombatStatus().getDebuffs().containsKey(debuff)) {
	//				return false;
	//			}
	//		}
	//		return true;
	//	}

}
