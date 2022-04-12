package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.character.inventory.CharacterWeapon;
import org.labcabrera.rolemaster.core.model.character.inventory.ItemStatus;
import org.labcabrera.rolemaster.core.model.item.WeaponCategory;
import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.model.npc.NpcAttack;
import org.labcabrera.rolemaster.core.model.tactical.ExhaustionPoints;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.PowerPoints;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterInventory;
import org.labcabrera.rolemaster.core.repository.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalNpcCharacterService {

	@Autowired
	private WeaponRepository weaponRepository;

	public Mono<TacticalCharacter> create(String tacticalSessionId, Npc npc) {
		return create(tacticalSessionId, npc, null);
	}

	public Mono<TacticalCharacter> create(String tacticalSessionId, Npc npc, Object npcCustomization) {
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
			.inventory(TacticalCharacterInventory.builder()
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

		return Mono.just(result)
			.flatMap(ta -> loadWeapons(ta, npc));
	}

	private Mono<TacticalCharacter> loadWeapons(TacticalCharacter tacticalCharacter, Npc npc) {
		return Mono
			.just(tacticalCharacter)
			.flatMap(ta -> {
				//TODO read all attacks
				return loadWeapon(ta, npc.getAttacks().iterator().next());
			})
			.map(e -> e);
	}

	private Mono<TacticalCharacter> loadWeapon(TacticalCharacter tacticalCharacter, NpcAttack attack) {
		String weaponId = attack.getWeaponId();
		if (tacticalCharacter.getNpcSkills() == null) {
			tacticalCharacter.setNpcSkills(new HashMap<>());
		}
		return Mono
			.just(tacticalCharacter)
			.zipWith(weaponRepository.findById(weaponId))
			.switchIfEmpty(Mono.error(() -> new RuntimeException("Missing weapon " + weaponId)))
			.map(pair -> {
				TacticalCharacter ta = pair.getT1();
				WeaponCategory weapon = pair.getT2();
				String skillId = weaponId;
				ItemStatus status = attack.getPrimary() ? ItemStatus.MAIN_HAND : ItemStatus.CARRIED;
				CharacterWeapon characterWeapon = CharacterWeapon.builder()
					.fumble(weapon.getFumble())
					.itemId(weaponId)
					.skillId(skillId)
					.weaponTableId(weaponId)
					.status(status)
					.build();
				ta.getInventory().getItems().add(characterWeapon);
				ta.getNpcSkills().put(skillId, attack.getBonus());
				return ta;
			});
	}

}
