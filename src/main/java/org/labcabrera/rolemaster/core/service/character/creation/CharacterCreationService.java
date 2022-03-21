package org.labcabrera.rolemaster.core.service.character.creation;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.service.character.AttributeCreationService;
import org.labcabrera.rolemaster.core.service.character.CharacterAdapter;
import org.labcabrera.rolemaster.core.table.attribute.AttributeBonusTable;
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
	private AttributeBonusTable attributeBonusTable;

	@Autowired
	private CharacterInfoRepository repository;

	@Autowired
	private List<CharacterAdapter> adapters;

	@Autowired
	private CharacterCreationSkillCategoryService skillCategoryService;

	@Autowired
	private CharacterCreationSkillService skillCreationService;

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
				.baseBonus(attributeBonusTable.getBonus(value))
				.build());
		});

		adapters.stream().forEach(adapter -> adapter.accept(character));

		Mono<CharacterInfo> monoCharacter = Mono.just(character)
			.flatMap(skillCategoryService::initialize)
			.flatMap(skillCreationService::initialize)
			.flatMap(repository::save);

		return monoCharacter;
	}

}
