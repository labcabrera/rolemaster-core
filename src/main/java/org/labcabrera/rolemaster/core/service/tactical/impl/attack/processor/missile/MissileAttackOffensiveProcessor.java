package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.missile;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.labcabrera.rolemaster.core.model.character.inventory.CharacterWeapon;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalSkillService;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.OffensiveHpBonusCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Component
public class MissileAttackOffensiveProcessor implements Function<TacticalActionMissileAttack, Mono<TacticalActionMissileAttack>> {

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	@Autowired
	private TacticalSkillService tacticalSkillService;

	@Autowired
	private OffensiveHpBonusCalculator offensiveHpBonusCalculator;

	@Override
	public Mono<TacticalActionMissileAttack> apply(TacticalActionMissileAttack action) {
		return Mono.just(action)
			.zipWith(tacticalCharacterRepository.findById(action.getSource()))
			.flatMap(this::loadSkillBonus)
			.map(this::loadHpBonus)
			.map(this::loadCoverBonus)
			.map(e -> e.getT1());
	}

	private Mono<Tuple2<TacticalActionMissileAttack, TacticalCharacter>> loadSkillBonus(
		Tuple2<TacticalActionMissileAttack, TacticalCharacter> pair) {
		TacticalCharacter ta = pair.getT2();
		CharacterWeapon weapon = ta.getInventory().getMainHandWeapon();
		String skillId = weapon.getSkillId();
		return Mono.just(pair).zipWith(tacticalSkillService.getSkill(ta, skillId), addSkillBonus);
	}

	private Tuple2<TacticalActionMissileAttack, TacticalCharacter> loadHpBonus(
		Tuple2<TacticalActionMissileAttack, TacticalCharacter> pair) {
		int bonus = offensiveHpBonusCalculator.getBonus(pair.getT2());
		pair.getT1().getOffensiveBonusMap().put("hp", bonus);
		return pair;
	}

	private Tuple2<TacticalActionMissileAttack, TacticalCharacter> loadCoverBonus(
		Tuple2<TacticalActionMissileAttack, TacticalCharacter> pair) {
		int modifier = 0;
		switch (pair.getT1().getCover()) {
		case NONE:
			break;
		}
		pair.getT1().getOffensiveBonusMap().put("cover", modifier);
		return pair;
	}

	private BiFunction<Tuple2<TacticalActionMissileAttack, TacticalCharacter>, Integer, Tuple2<TacticalActionMissileAttack, TacticalCharacter>> addSkillBonus = (
		a, b) -> {
		a.getT1().getOffensiveBonusMap().put("skill", b);
		return a;
	};

}
