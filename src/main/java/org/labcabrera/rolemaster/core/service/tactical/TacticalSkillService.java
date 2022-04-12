package org.labcabrera.rolemaster.core.service.tactical;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalSkillService {

	public Mono<Integer> getSkill(TacticalCharacter character, String skill) {
		return Mono.just(0);
	}

}
