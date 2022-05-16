package org.labcabrera.rolemaster.core.service.tactical;

import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.service.converter.NpcToTacticalCharacterConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalNpcCharacterServiceImpl implements TacticalNpcCharacterService {

	@Autowired
	private NpcNameGenerator npcNameGenerator;

	@Autowired
	private NpcToTacticalCharacterConverter converter;

	@Override
	public Mono<TacticalCharacter> create(String tacticalSessionId, Npc npc) {
		return create(tacticalSessionId, npc, null);
	}

	@Override
	public Mono<TacticalCharacter> create(String tacticalSessionId, Npc npc, NpcCustomization npcCustomization) {
		TacticalCharacter result = converter.convert(tacticalSessionId, npc, npcCustomization);
		if (npc.getAttackQuickness() != null) {
			result.getModifiers().setInitiative(npc.getAttackQuickness().getInitiativeModifier());
		}
		else {
			result.getModifiers().setInitiative(0);
		}
		return Mono.just(result)
			.zipWith(npcNameGenerator.generateName(tacticalSessionId, npc), (a, b) -> {
				if (a.getName() == null) {
					a.setName(b);
				}
				return a;
			});
	}

}
