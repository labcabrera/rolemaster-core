package org.labcabrera.rolemaster.core.service.combat;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.combat.Attack;
import org.labcabrera.rolemaster.core.model.combat.AttackState;
import org.labcabrera.rolemaster.core.repository.AttackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CombatService {

	@Autowired
	private AttackRepository attackRepository;

	public Mono<Attack> prepare(Attack attack) {
		//TODO
		attack.setState(AttackState.UNRESOLVED);
		attack.setMetadata(EntityMetadata.builder()
			.created(LocalDateTime.now())
			.build());

		return attackRepository.save(attack);
	}

	public Mono<Attack> resolve(String attackId, OpenRoll roll) {
		return attackRepository
			.findById(attackId)
			.map(attack -> {
				attack.setOpenRoll(roll);
				attack.setState(AttackState.RESOLVED);
				return attack;
			})
			.flatMap(attackRepository::save);
	}

}
