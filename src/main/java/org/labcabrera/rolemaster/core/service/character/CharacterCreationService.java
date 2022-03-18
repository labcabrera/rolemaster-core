package org.labcabrera.rolemaster.core.service.character;

import java.util.Arrays;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CharacterCreationService {

	@Autowired
	private CharacterInfoRepository repository;

	public Mono<CharacterInfo> create(CharacterCreationRequest request) {
		CharacterInfo character = CharacterInfo.builder()
			.name(request.getName())
			.raceId(request.getRaceId())
			.build();

		Arrays.asList(AttributeType.values()).stream().forEach(e -> {
			character.getAttributes().put(e, new CharacterAttribute());
		});
		return repository.insert(character);
	}

}
