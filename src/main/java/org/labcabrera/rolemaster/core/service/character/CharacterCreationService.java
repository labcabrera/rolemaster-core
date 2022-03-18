package org.labcabrera.rolemaster.core.service.character;

import java.util.Arrays;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CharacterCreationService {

	@Autowired
	private AttributeCreationService attributeCreationService;

	@Autowired
	private AttributeService attributeService;

	@Autowired
	private CharacterInfoRepository repository;

	@Autowired
	private RaceRepository raceRepository;

	public Mono<CharacterInfo> create(CharacterCreationRequest request) {
		log.info("Processing new character {}", request.getName());

		final CharacterInfo character = CharacterInfo.builder()
			.name(request.getName())
			.raceId(request.getRaceId())
			.build();

		Arrays.asList(AttributeType.values()).stream().forEach(e -> {
			int value = request.getBaseAttributes().containsKey(e) ? request.getBaseAttributes().get(e) : 1;
			character.getAttributes().put(e, CharacterAttribute.builder()
				.currentValue(value)
				.potentialValue(attributeCreationService.getPotentialStat(value))
				.baseBonus(attributeService.getBonus(value))
				.build());
		});

		raceRepository.findById(request.getRaceId()).subscribe(race -> {
			log.info("Race: {}, character: {}", race, character);
		});

		return repository.insert(character);
	}

}
