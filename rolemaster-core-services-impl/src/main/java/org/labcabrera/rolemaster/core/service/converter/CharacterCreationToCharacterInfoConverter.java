package org.labcabrera.rolemaster.core.service.converter;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterStatus;
import org.labcabrera.rolemaster.core.model.character.CharacterWeight;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreation;
import org.labcabrera.rolemaster.core.service.table.character.ExperienceLevelTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CharacterCreationToCharacterInfoConverter implements Converter<CharacterCreation, CharacterInfo> {

	@Autowired
	private ExperienceLevelTable experienceLevelTable;

	@Override
	public CharacterInfo convert(CharacterCreation request) {
		return CharacterInfo.builder()
			.universeId(request.getUniverseId())
			.realm(request.getRealm())
			.level(0)
			.maxLevel(request.getLevel())
			.xp(null)
			.xp(experienceLevelTable.getRequiredExperience(request.getLevel()))
			.name(request.getName())
			.raceId(request.getRaceId())
			.professionId(request.getProfessionId())
			.age(request.getAge())
			.height(request.getHeight())
			.weight(CharacterWeight.builder()
				.weight(request.getWeight())
				.build())
			.status(CharacterStatus.PARTIALLY_CREATED)
			.build();
	}

}
