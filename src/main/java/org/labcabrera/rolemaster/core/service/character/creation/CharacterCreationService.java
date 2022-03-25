package org.labcabrera.rolemaster.core.service.character.creation;

import java.util.Arrays;

import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterCreationStatus;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContextImpl;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.ProfessionRepository;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.labcabrera.rolemaster.core.service.character.adapter.CharacterAttributesAdapter;
import org.labcabrera.rolemaster.core.service.character.adapter.CharacterDevelopmentAdapter;
import org.labcabrera.rolemaster.core.service.character.adapter.CharacterExhaustionAdapter;
import org.labcabrera.rolemaster.core.service.character.adapter.CharacterHpAdapter;
import org.labcabrera.rolemaster.core.service.character.adapter.CharacterSkillAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CharacterCreationService {

	@Autowired
	private AttributeCreationService attributeCreationService;

	@Autowired
	private CharacterInfoRepository repository;

	@Autowired
	private CharacterSkillAdapter skillAdapter;

	@Autowired
	private CharacterAttributesAdapter characterAttributesAdapter;

	@Autowired
	private CharacterHpAdapter hpAdapter;

	@Autowired
	private CharacterExhaustionAdapter exhaustionAdapter;

	@Autowired
	private CharacterCreationSkillCategoryService skillCategoryService;

	@Autowired
	private CharacterCreationSkillService skillCreationService;

	@Autowired
	private CharacterDevelopmentAdapter characterDevelopmentAdapter;

	@Autowired
	private RaceRepository raceRepository;

	@Autowired
	private ProfessionRepository professionRepository;

	@Autowired
	private SkillCategoryRepository skillCategoryRepository;

	@Autowired
	private SkillRepository skillRepository;

	public Mono<CharacterInfo> create(CharacterCreationRequest request) {
		log.info("Processing new character {}", request.getName());

		final CharacterInfo character = CharacterInfo.builder()
			.level(1)
			.name(request.getName())
			.raceId(request.getRaceId())
			.professionId(request.getProfessionId())
			.age(request.getAge())
			.creationStatus(CharacterCreationStatus.PARTIALLY_CREATED)
			.build();

		loadAttributes(character, request);

		final CharacterModificationContext context = CharacterModificationContextImpl.builder()
			.character(character)
			.build();

		return Mono.just(context)
			.zipWith(raceRepository.findById(request.getRaceId()))
			.switchIfEmpty(Mono.error(new NotFoundException("Race " + request.getRaceId() + " not found")))
			.map(tuple -> {
				tuple.getT1().setRace(tuple.getT2());
				return tuple.getT1();
			})
			.zipWith(professionRepository.findById(request.getProfessionId()))
			.switchIfEmpty(Mono.error(new NotFoundException("Profession " + request.getProfessionId() + " not found")))
			.map(tuple -> {
				tuple.getT1().setProfession(tuple.getT2());
				return tuple.getT1();
			})
			.flatMap(ctx -> {
				return skillCategoryRepository.findAll(Sort.by("id"))
					.collectList()
					.doOnNext(list -> ctx.setSkillCategories(list))
					.map(e -> ctx);
			})
			.flatMap(ctx -> {
				return skillRepository.findSkillsOnNewCharacter()
					.collectList()
					.doOnNext(list -> ctx.setSkills(list))
					.map(e -> ctx);
			})
			.map(characterAttributesAdapter::apply)
			.map(skillCategoryService::initialize)
			.map(skillCreationService::initialize)
			.map(skillAdapter::apply)
			.map(hpAdapter::apply)
			.map(exhaustionAdapter::apply)
			.map(characterDevelopmentAdapter::apply)
			.map(ctx -> ctx.getCharacter())
			.flatMap(repository::save)
			.doOnNext(e -> log.info("Created character {}", e))
			.map(e -> e);
	}

	private void loadAttributes(CharacterInfo character, CharacterCreationRequest request) {
		Arrays.asList(AttributeType.values()).stream().forEach(e -> {
			int value = request.getBaseAttributes().containsKey(e) ? request.getBaseAttributes().get(e) : 1;
			character.getAttributes().put(e, CharacterAttribute.builder()
				.currentValue(value)
				.potentialValue(attributeCreationService.getPotentialStat(value))
				.build());
		});
	}

}
