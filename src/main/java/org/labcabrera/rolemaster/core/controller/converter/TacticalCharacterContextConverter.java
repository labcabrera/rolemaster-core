package org.labcabrera.rolemaster.core.controller.converter;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.model.tactical.TacticalNpcInstance;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.springframework.stereotype.Component;

@Component
public class TacticalCharacterContextConverter {

	public TacticalCharacterContext convert(TacticalSession tacticalSession, CharacterInfo character) {
		return TacticalCharacterContext.builder()
			.tacticalSessionId(tacticalSession.getId())
			.characterId(character.getId())
			.isNpc(false)
			.name(character.getName())
			.hp(getHp(character.getMaxHp()))
			.metadata(EntityMetadata.builder().created(LocalDateTime.now()).build())
			.build();
	}

	public TacticalCharacterContext convert(TacticalSession tacticalSession, TacticalNpcInstance npcInstance) {
		return TacticalCharacterContext.builder()
			.tacticalSessionId(tacticalSession.getId())
			.characterId(npcInstance.getId())
			.isNpc(true)
			.name(npcInstance.getName())
			//TODO
			.hp(getHp(100))
			.metadata(EntityMetadata.builder().created(LocalDateTime.now()).build())
			.build();
	}

	private Hp getHp(Integer value) {
		return Hp.builder().max(value).current(value).build();
	}
}
