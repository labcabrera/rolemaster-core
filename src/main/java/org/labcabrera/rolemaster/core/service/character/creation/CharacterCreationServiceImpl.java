package org.labcabrera.rolemaster.core.service.character.creation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.AttributeBonusType;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.BonusType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterResistance;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.character.ResistanceBonusType;
import org.labcabrera.rolemaster.core.model.character.ResistanceType;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreation;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContextImpl;
import org.labcabrera.rolemaster.core.model.skill.SkillCategory;
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

	@Override
	public Mono<CharacterInfo> create(CharacterCreation request) {
		log.info("Processing new character {}", request.getName());
		CharacterInfo character = converter.convert(request);
		CharacterModificationContext context = CharacterModificationContextImpl.builder()
			.character(character)
			.build();
		return Mono.just(context)
			.zipWith(raceRepository.findById(request.getRaceId()))
			.switchIfEmpty(Mono.error(new BadRequestException("Race " + request.getRaceId() + " not found")))
			.map(tuple -> {
				CharacterModificationContext ctx = tuple.getT1();
				Race race = tuple.getT2();
				ctx.setRace(race);
				ctx.getCharacter().setBodyDevelopmentProgression(race.getBodyDevelopmentProgression());
				ctx.getCharacter().setPowerPointProgression(race.getPowerPointsProgression().get(ctx.getCharacter().getRealm()));
				ctx.getCharacter().getDevelopmentPoints().setBackgroundOptions(race.getBackgroundOptions());
				ctx.getCharacter().getNotes().addAll(race.getSpecialAbilities());
				return ctx;
			})
			.zipWith(professionRepository.findById(request.getProfessionId()))
			.switchIfEmpty(Mono.error(new BadRequestException("Profession " + request.getProfessionId() + " not found")))
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
			.map(this::loadSkillCategories)
			.map(ctx -> loadSkillCategoryWeapons(ctx, request))
			.map(this::loadDefaultSkills)
			.map(this::loadResistances)
			.map(CharacterModificationContext::getCharacter)
			.map(postProcessorService)
			.flatMap(repository::save)
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

	private CharacterModificationContext loadSkillCategories(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		Race race = context.getRace();
		Profession profession = context.getProfession();
		context.getSkillCategories().stream().forEach(category -> {
			String categoryId = category.getId();
			int adolescenceRank = race.getAdolescenceSkillCategoryRanks().getOrDefault(categoryId, 0);
			int bonusProfession = profession.getSkillCategoryBonus().getOrDefault(categoryId, 0);
			int bonusAttribute = getAttributeBonus(category, character);
			CharacterSkillCategory characterSkillCategory = CharacterSkillCategory.builder()
				.categoryId(category.getId())
				.developmentCost(profession.getSkillCategoryDevelopmentCost().getOrDefault(categoryId, new ArrayList<>()))
				.attributes(category.getAttributeBonus())
				.group(category.getGroup())
				.progressionType(category.getProgressionType())
				.build();
			characterSkillCategory.getRanks().put(RankType.ADOLESCENCE, adolescenceRank);
			characterSkillCategory.getBonus().put(BonusType.PROFESSION, bonusProfession);
			characterSkillCategory.getBonus().put(BonusType.ATTRIBUTE, bonusAttribute);
			character.getSkillCategories().add(characterSkillCategory);
		});
		return context;
	}

	private CharacterModificationContext loadDefaultSkills(CharacterModificationContext context) {
		Race race = context.getRace();
		context.getSkills().stream().forEach(skill -> {
			String categoryId = skill.getCategoryId();
			String skillId = skill.getId();
			Integer adolescenceRanks = race.getAdolescenceSkillRanks().getOrDefault(skill.getId(), 0);
			if (skill.getCustomizableOptions() > 0) {
				List<Entry<String, Integer>> list = race.getAdolescenceSkillRanks().entrySet().stream()
					.filter(e -> e.getKey().startsWith(skill.getId()))
					.toList();
				if (!list.isEmpty()) {
					skillId = list.iterator().next().getKey();
					adolescenceRanks = list.iterator().next().getValue();
				}
			}
			CharacterSkillCategory category = context.getCharacter().getSkillCategory(categoryId)
				.orElseThrow(() -> new BadRequestException("Invalid skill category " + categoryId));
			CharacterSkill cs = CharacterSkill.builder()
				.skillId(skillId)
				.categoryId(skill.getCategoryId())
				.group(category.getGroup())
				.developmentCost(category.getDevelopmentCost())
				.attributes(category.getAttributes())
				.progressionType(skill.getProgressionType())
				.build();
			cs.getRanks().put(RankType.ADOLESCENCE, adolescenceRanks);
			cs.getRanks().put(RankType.CONSOLIDATED, 0);
			cs.getRanks().put(RankType.DEVELOPMENT, 0);
			cs.getBonus().put(BonusType.SKILL_SPECIAL, skill.getSkillBonus());
			context.getCharacter().getSkills().add(cs);
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
				.findFirst().orElseThrow(() -> new BadRequestException("Invalid weapon skill category " + categoryId));
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

	private Integer getAttributeBonus(SkillCategory category, CharacterInfo characterInfo) {
		int result = 0;
		for (AttributeType at : category.getAttributeBonus()) {
			result += characterInfo.getAttributes().get(at).getTotalBonus();
		}
		return result;
	}

	private CharacterModificationContext checkRealm(CharacterModificationContext context) {
		List<Realm> availableRealms = context.getProfession().getAvailableRealms();
		if (!availableRealms.contains(context.getCharacter().getRealm())) {
			throw new BadRequestException("Invalid realm.");
		}
		return context;
	}
}
