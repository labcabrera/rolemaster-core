package org.labcabrera.rolemaster.core.service.character;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CharacterLevelUpServiceImpl implements CharacterLevelUpService {

	@Autowired
	private CharacterInfoRepository characterRepository;

	@Override
	public Mono<CharacterInfo> levelUp(String characterId) {
		return characterRepository.findById(characterId)
			.switchIfEmpty(Mono.error(() -> null))
			.map(character -> {
				if (character.getLevel() >= character.getMaxLevel()) {
					throw new BadRequestException("The character already has the maximum level");
				}
				character.setLevel(character.getLevel() + 1);
				return character;
			})
			.flatMap(characterRepository::save);
	}
}
