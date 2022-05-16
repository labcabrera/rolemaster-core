package org.labcabrera.rolemaster.core.service.tactical.maneuvers;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovingManeuver;
import org.labcabrera.rolemaster.core.service.tactical.TacticalSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

@Component
public class MovingManeuversBonusProcessor {

	@Autowired
	private TacticalSkillService skillService;

	public Mono<Map<String, Integer>> getBonusMap(TacticalActionMovingManeuver action, TacticalCharacter tc, Map<String, Integer> map) {
		return Mono.zip(Mono.just(action), Mono.just(tc), Mono.just(map))
			.flatMap(this::processSkill)
			.map(Tuple3::getT3);
	}

	private Mono<Tuple3<TacticalActionMovingManeuver, TacticalCharacter, Map<String, Integer>>> processSkill(
		Tuple3<TacticalActionMovingManeuver, TacticalCharacter, Map<String, Integer>> tuple) {

		TacticalCharacter tc = tuple.getT2();
		String skillId = tuple.getT1().getSkillId();
		return skillService.getSkill(tc, skillId)
			.map(skillBonus -> {
				tuple.getT3().put("skill", skillBonus);
				return tuple;
			});
	}

}
