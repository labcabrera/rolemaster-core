package org.labcabrera.rolemaster.core.services.commons.converter;

import java.util.LinkedHashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.dto.character.CharacterCreation;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterStatus;
import org.labcabrera.rolemaster.core.model.character.CharacterWeight;
import org.labcabrera.rolemaster.core.table.rmss.character.ExperienceLevelTable;
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
			.version(request.getVersion())
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
			.attributes(getAttributeMap(request))
			.status(CharacterStatus.PARTIALLY_CREATED)
			.build();
	}

	private Map<AttributeType, CharacterAttribute> getAttributeMap(CharacterCreation request) {
		Map<AttributeType, CharacterAttribute> map = new LinkedHashMap<>();
		for (AttributeType att : AttributeType.values()) {
			CharacterAttribute ca = CharacterAttribute.builder()
				.currentValue(request.getBaseAttributes().get(att))
				.build();
			map.put(att, ca);
		}
		return map;
	}

}
