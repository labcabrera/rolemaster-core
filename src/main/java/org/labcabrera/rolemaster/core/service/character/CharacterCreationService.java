package org.labcabrera.rolemaster.core.service.character;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
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
			.metadata(EntityMetadata.builder()
				.created(LocalDateTime.now())
				.build())
			.build();
		return repository.insert(character);
	}

}
