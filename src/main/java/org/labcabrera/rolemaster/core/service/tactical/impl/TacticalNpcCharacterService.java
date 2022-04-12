package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.model.tactical.ExhaustionPoints;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.PowerPoints;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterItems;
import org.springframework.stereotype.Service;

@Service
public class TacticalNpcCharacterService {

	public TacticalCharacter create(String tacticalSessionId, Npc npc) {
		Integer level = npc.getLevel();
		Integer maxHp = npc.getHp();

		BigDecimal exhaustionPoints = new BigDecimal(100);

		TacticalCharacter result = TacticalCharacter.builder()
			.tacticalSessionId(tacticalSessionId)
			.name("")
			.level(level)
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
				.max(exhaustionPoints)
				.current(exhaustionPoints)
				.build())
			.items(TacticalCharacterItems.builder()
				.mainWeaponId(npc.getAttacks().iterator().next().getWeaponId())
				.mainWeaponBonus(npc.getAttacks().iterator().next().getBonus())
				.build())
			.armorType(npc.getArmorType())
			.baseDefensiveBonus(npc.getDefensiveBonus())
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

		return result;
	}

}
