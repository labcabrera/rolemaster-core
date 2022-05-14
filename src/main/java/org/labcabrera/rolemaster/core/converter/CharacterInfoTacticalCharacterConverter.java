package org.labcabrera.rolemaster.core.converter;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.ContextCharacterModifiers;
import org.labcabrera.rolemaster.core.model.tactical.ExhaustionPoints;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.PowerPoints;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.repository.CharacterItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class CharacterInfoTacticalCharacterConverter {

	@Autowired
	private CharacterItemRepository itemRepository;

	public Mono<TacticalCharacter> convert(TacticalSession tacticalSession, CharacterInfo character) {
		return itemRepository.findByCharacterId(character.getId())
			.collectList()
			.map(items -> TacticalCharacter.builder()
				.tacticalSessionId(tacticalSession.getId())
				.characterId(character.getId())
				.level(character.getLevel())
				.isNpc(false)
				.name(character.getName())
				.shortDescription(character.getProfessionId() + " " + character.getLevel())
				.hp(Hp.builder()
					.max(character.getMaxHp())
					.current(character.getMaxHp())
					.build())
				.powerPoints(PowerPoints.builder()
					.max(character.getMaxPowerPoints())
					.current(character.getMaxPowerPoints())
					.build())
				.exhaustionPoints(ExhaustionPoints.builder()
					.max(character.getMaxExhaustionPoints())
					.current(character.getMaxExhaustionPoints())
					.build())
				.defensiveBonus(character.getDefensiveBonus())
				.armor(character.getArmor().getArmor())
				.items(items)
				.modifiers(ContextCharacterModifiers.builder()
					.initiative(character.getAttributes().get(AttributeType.QUICKNESS).getCurrentValue())
					.build())
				.baseMovementRate(character.getBaseMovementRate())
				.mmBonus(character.getArmor().getArmorPenalty())
				.metadata(EntityMetadata.builder().created(LocalDateTime.now()).build())
				.build());
	}

}
