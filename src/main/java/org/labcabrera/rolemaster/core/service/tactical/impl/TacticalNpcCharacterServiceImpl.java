package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.character.inventory.CharacterWeapon;
import org.labcabrera.rolemaster.core.model.character.inventory.ItemStatus;
import org.labcabrera.rolemaster.core.model.item.Weapon;
import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.model.tactical.ExhaustionPoints;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.PowerPoints;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterInventory;
import org.labcabrera.rolemaster.core.repository.WeaponRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalNpcCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalNpcCharacterServiceImpl implements TacticalNpcCharacterService {

	@Autowired
	private WeaponRepository weaponRepository;

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

		BigDecimal exhaustionPoints = new BigDecimal(100);

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
				.max(exhaustionPoints)
				.current(exhaustionPoints)
				.build())
			.inventory(TacticalCharacterInventory.builder()
				.build())
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
			.flatMap(ta -> loadWeapons(ta, npc))
			.zipWith(npcNameGenerator.generateName(tacticalSessionId, npc), (a, b) -> {
				if (a.getName() == null) {
					a.setName(b);
				}
				return a;
			});
	}

	private Mono<TacticalCharacter> loadWeapons(TacticalCharacter tacticalCharacter, Npc npc) {
		return Mono.just(tacticalCharacter)
			.zipWith(readWeapons(npc))
			.map(pair -> loadWeapons(pair.getT1(), npc, pair.getT2()));
	}

	private TacticalCharacter loadWeapons(TacticalCharacter tacticalCharacter, Npc npc, List<Weapon> weapons) {
		npc.getAttacks().stream().forEach(attack -> {
			String weaponId = attack.getWeaponId();
			if (tacticalCharacter.getNpcSkills() == null) {
				tacticalCharacter.setNpcSkills(new HashMap<>());
			}
			Weapon weapon = weapons.stream().filter(e -> e.getId().equals(weaponId)).findFirst()
				.orElseThrow(() -> new RuntimeException("Weapon " + weaponId + " not found"));
			String skillId = weaponId;
			ItemStatus status = attack.getPrimary() ? ItemStatus.MAIN_HAND : ItemStatus.CARRIED;
			CharacterWeapon characterWeapon = CharacterWeapon.builder()
				.fumble(weapon.getFumble())
				.itemId(weaponId)
				.skillId(skillId)
				.weaponTableId(weaponId)
				.status(status)
				.build();
			tacticalCharacter.getInventory().getItems().add(characterWeapon);
			tacticalCharacter.getNpcSkills().put(skillId, attack.getBonus());
		});
		return tacticalCharacter;
	}

	private Mono<List<Weapon>> readWeapons(Npc npc) {
		return weaponRepository.findAll().collectList();
	}

}
