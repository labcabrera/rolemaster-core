package org.labcabrera.rolemaster.core.service.tactical;

import java.util.Optional;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.NpcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalSkillService {

	@Autowired
	private NpcRepository npcRepository;

	@Autowired
	private CharacterInfoRepository characterInfoRepository;

	public Mono<Integer> getSkill(TacticalCharacter character, String skill) {
		if (character.isNpc()) {
			return getNpcSkill(character, skill);
		}
		else {
			return getCharacterSkill(character, skill);
		}
	}

	public String getTwoWeaponSkill(String mainHandSkillId, String offHandSkillId) {
		return "two-weapon-combat:" + mainHandSkillId + ":" + offHandSkillId;
	}

	private Mono<Integer> getNpcSkill(TacticalCharacter character, String skillId) {
		return npcRepository.findById(character.getCharacterId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("NPC not found")))
			.map(npc -> npc.getSkills().getOrDefault(skillId, 0));
	}

	private Mono<Integer> getCharacterSkill(TacticalCharacter character, String skill) {
		return characterInfoRepository.findById(character.getCharacterId())
			.map(c -> {
				Optional<CharacterSkill> optional = c.getSkill(skill);
				if (optional.isEmpty()) {
					return 0;
				}
				else {
					return optional.get().getTotalBonus();
				}
			});
	}

}
