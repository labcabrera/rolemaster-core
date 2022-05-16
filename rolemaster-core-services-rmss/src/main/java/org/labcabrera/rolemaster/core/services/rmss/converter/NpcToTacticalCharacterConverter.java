package org.labcabrera.rolemaster.core.services.rmss.converter;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.model.tactical.ExhaustionPoints;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.PowerPoints;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springframework.stereotype.Component;

@Component
public class NpcToTacticalCharacterConverter {

	public TacticalCharacter convert(String tacticalSessionId, Npc npc) {
		return convert(tacticalSessionId, npc, null);
	}

	public TacticalCharacter convert(String tacticalSessionId, Npc npc, NpcCustomization npcCustomization) {
		Integer level = npc.getLevel();
		Integer maxHp = npc.getHp();
		return TacticalCharacter.builder()
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
			.specialAttacks(npc.getSpecialAttacks())
			.armor(npc.getArmorType())
			.defensiveBonus(npc.getDefensiveBonus())
			.baseMovementRate(npc.getBaseMovementRate())
			.mmBonus(npc.getMmBonus())
			.metadata(EntityMetadata.builder()
				.created(LocalDateTime.now())
				.build())
			.build();
	}

}
