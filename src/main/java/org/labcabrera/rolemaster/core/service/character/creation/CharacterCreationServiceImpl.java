package org.labcabrera.rolemaster.core.service.character.creation;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.message.Messages.Errors;
import org.labcabrera.rolemaster.core.model.character.AttributeBonusType;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterResistance;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.ResistanceBonusType;
import org.labcabrera.rolemaster.core.model.character.ResistanceType;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreation;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContextImpl;
import org.labcabrera.rolemaster.core.model.spell.Realm;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.ProfessionRepository;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.labcabrera.rolemaster.core.service.character.processor.CharacterPostProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CharacterCreationServiceImpl implements CharacterCreationService {

	private static final String MSG_INVALID_WEAPON_SIZE = "Invalid request weapon category order count. Expected: %s, received: %s";

	@Autowired
	private AttributeCreationService attributeCreationService;

	@Autowired
	private CharacterInfoRepository repository;

	@Autowired
	private CharacterPostProcessorService postProcessorService;

	@Autowired
	private RaceRepository raceRepository;

	@Autowired
	private ProfessionRepository professionRepository;

	@Autowired
	private SkillCategoryRepository skillCategoryRepository;

	@Autowired
	private CharacterCreationSkillService characterCreationSkillService;

	@Autowired
	private Converter<CharacterCreation, CharacterInfo> converter;

	@Autowired
	private CharacterCreationItemLoader characterCreationItemLoader;

	@Autowired
	private CharacterCreationRaceProcessor characterCreationRaceProcessor;

	@Autowired
	private CharacterCreationSkillCategoryProcessor characterCreationSkillCategoryProcessor;

	@Autowired
	private CharacterCreationSkillProcessor characterCreationSkillProcessor;

	@Override
	public Mono<CharacterInfo> create(CharacterCreation request) {
		log.info("Processing new character {}", request.getName());
		CharacterInfo character = converter.convert(request);
		CharacterModificationContext context = CharacterModificationContextImpl.builder()
			.character(character)
			.build();
		return Mono.just(context)
			.zipWith(raceRepository.findById(request.getRaceId()))
			.switchIfEmpty(Mono.error(new BadRequestException(Errors.raceNotFound(request.getRaceId()))))
			.map(characterCreationRaceProcessor::process)
			.zipWith(professionRepository.findById(request.getProfessionId()))
			.switchIfEmpty(Mono.error(new BadRequestException(Errors.professionNotFound(request.getProfessionId()))))
			.map(tuple -> {
				tuple.getT1().setProfession(tuple.getT2());
				return tuple.getT1();
			})
			.map(this::checkRealm)
			.flatMap(ctx -> skillCategoryRepository.findAll(Sort.by("id"))
				.collectList()
				.doOnNext(ctx::setSkillCategories)
				.map(e -> ctx))
			.flatMap(ctx -> characterCreationSkillService.getSkills(ctx.getRace())
				.collectList()
				.doOnNext(ctx::setSkills)
				.map(e -> ctx))
			.map(ctx -> loadAttributes(ctx, request))
			.map(characterCreationSkillCategoryProcessor::loadSkillCategories)
			.map(ctx -> loadSkillCategoryWeapons(ctx, request))
			.map(characterCreationSkillProcessor::loadSkills)
			.map(this::loadResistances)
			.map(CharacterModificationContext::getCharacter)
			.map(postProcessorService)
			.flatMap(repository::save)
			.flatMap(characterCreationItemLoader::addItems)
			.doOnNext(e -> log.info("Created character {}", e))
			.map(e -> e);
	}

	private CharacterModificationContext loadAttributes(CharacterModificationContext context, CharacterCreation request) {
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

	private CharacterModificationContext loadSkillCategoryWeapons(CharacterModificationContext context, CharacterCreation request) {
		CharacterInfo character = context.getCharacter();
		Profession profession = context.getProfession();
		int sizeExpected = profession.getSkillCategoryWeaponDevelopmentCost().size();
		int sizeReceived = request.getWeaponCategoryPriority().size();
		if (sizeExpected != sizeReceived) {
			throw new BadRequestException(String.format(MSG_INVALID_WEAPON_SIZE, sizeExpected, sizeReceived));
		}
		for (int i = 0; i < request.getWeaponCategoryPriority().size(); i++) {
			String categoryId = request.getWeaponCategoryPriority().get(i);
			List<Integer> devCost = profession.getSkillCategoryWeaponDevelopmentCost().get(i);
			CharacterSkillCategory category = character.getSkillCategories().stream()
				.filter(e -> e.getCategoryId().equals(categoryId))
				.findFirst().orElseThrow(() -> new BadRequestException(Errors.invalidSkillCategory(categoryId)));
			category.setDevelopmentCost(devCost);
		}
		return context;
	}

	private CharacterModificationContext loadResistances(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		Race race = context.getRace();
		for (ResistanceType r : ResistanceType.values()) {
			int raceBonus = race.getResistanceBonus().getOrDefault(r, 0);
			CharacterResistance cr = CharacterResistance.builder()
				.build();
			cr.getBonus().put(ResistanceBonusType.RACE, raceBonus);
			character.getResistances().put(r, cr);

		}
		return context;
	}

	private CharacterModificationContext checkRealm(CharacterModificationContext context) {
		Realm realm = context.getCharacter().getRealm();
		List<Realm> availableRealms = context.getProfession().getAvailableRealms();
		if (!availableRealms.contains(realm)) {
			throw new BadRequestException(Errors.invalidRealm(realm));
		}
		return context;
	}

}
