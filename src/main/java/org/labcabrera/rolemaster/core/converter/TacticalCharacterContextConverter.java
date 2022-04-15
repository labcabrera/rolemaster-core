package org.labcabrera.rolemaster.core.converter;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.springframework.stereotype.Component;

@Component
public class TacticalCharacterContextConverter {

	public TacticalCharacter convert(TacticalSession tacticalSession, CharacterInfo character) {
		return TacticalCharacter.builder()
			.tacticalSessionId(tacticalSession.getId())
			.characterId(character.getId())
			.shortDescription(character.getProfessionId() + " " + character.getLevel())
			.isNpc(false)
			.name(character.getName())
			.hp(getHp(character.getMaxHp()))
			.metadata(EntityMetadata.builder().created(LocalDateTime.now()).build())
			.build();
	}

	private Hp getHp(Integer value) {
		return Hp.builder().max(value).current(value).build();
	}
}
