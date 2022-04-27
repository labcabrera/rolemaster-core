package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.model.tactical.ExhaustionPoints;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.PowerPoints;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.service.tactical.TacticalNpcCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalNpcCharacterServiceImpl implements TacticalNpcCharacterService {

	@Autowired
	private NpcNameGenerator npcNameGenerator;

	@Override
	public Mono<TacticalCharacter> create(String tacticalSessionId, Npc npc) {
		return create(tacticalSessionId, npc, null);
	}

	@Override
	public Mono<TacticalCharacter> create(String tacticalSessionId, Npc npc, NpcCustomization npcCustomization) {
		Integer level = npc.getLevel();
		Integer maxHp = npc.getHp();
		TacticalCharacter result = TacticalCharacter.builder()
			.name(npcCustomization != null ? npcCustomization.getName() : null)
			.shortDescription(npc.getShortDescription())
			.tacticalSessionId(tacticalSessionId)
			.level(level)
			.isNpc(true)
			.characterId(npc.getId())
			.hp(Hp.builder()
				.max(maxHp)
				.current(maxHp)
				.build())
			.powerPoints(PowerPoints.builder()
				.max(100)
				.current(100)
				.build())
			.exhaustionPoints(ExhaustionPoints.builder()
				.max(npc.getExhaustionPoints())
				.current(npc.getExhaustionPoints())
				.build())
			.items(npc.getItems())
			.armor(npc.getArmorType())
			.defensiveBonus(npc.getDefensiveBonus())
			.metadata(EntityMetadata.builder()
				.created(LocalDateTime.now())
				.build())
			.build();

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
