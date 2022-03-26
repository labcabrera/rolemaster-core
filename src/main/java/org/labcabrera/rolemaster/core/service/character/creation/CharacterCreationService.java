package org.labcabrera.rolemaster.core.service.character.creation;

import java.util.Arrays;

import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.character.AttributeBonusType;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterCreationStatus;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContextImpl;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.ProfessionRepository;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.labcabrera.rolemaster.core.service.character.processor.CharacterPostProcessorService;
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
	private CharacterPostProcessorService postProcessorService;

	@Autowired
	private CharacterCreationSkillCategoryService skillCategoryService;

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
			.height(request.getHeight())
			.weight(request.getWeight())
			.xp(0)
			.creationStatus(CharacterCreationStatus.PARTIALLY_CREATED)
			.build();

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
			.map(ctx -> loadAttributes(ctx, request))
			.map(skillCategoryService::initialize)
			.map(this::loadSkills)
			.map(e -> {
				postProcessorService.apply(e.getCharacter());
				return e;
			})
			.map(ctx -> ctx.getCharacter())
			.flatMap(repository::save)
			.doOnNext(e -> log.info("Created character {}", e))
			.map(e -> e);
	}

	private CharacterModificationContext loadAttributes(CharacterModificationContext context, CharacterCreationRequest request) {
		Arrays.asList(AttributeType.values()).stream().forEach(e -> {
			int value = request.getBaseAttributes().containsKey(e) ? request.getBaseAttributes().get(e) : 1;
			int potentialValue = attributeCreationService.getPotentialStat(value);
			int bonusRace = context.getRace().getAttributeModifiers().getOrDefault(e, 0);
			CharacterAttribute characterAttribute = CharacterAttribute.builder()
				.currentValue(value)
				.potentialValue(potentialValue)
				.build();
			characterAttribute.getBonus().put(AttributeBonusType.RACE, bonusRace);
			context.getCharacter().getAttributes().put(e, characterAttribute);
		});
		return context;
	}

	private CharacterModificationContext loadSkills(CharacterModificationContext context) {
		context.getSkills().stream().forEach(skill -> {
			CharacterSkill cs = CharacterSkill.builder()
				.skillId(skill.getId())
				.attributes(skill.getAttributeBonus())
				.build();
			context.getCharacter().getSkills().add(cs);
		});
		return context;
	}

}
