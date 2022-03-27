package org.labcabrera.rolemaster.core.service.character.creation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.AttributeBonusType;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.BonusType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterCreationStatus;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterResistance;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.character.ResistanceBonusType;
import org.labcabrera.rolemaster.core.model.character.ResistanceType;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContextImpl;
import org.labcabrera.rolemaster.core.model.skill.SkillCategory;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.ProfessionRepository;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.repository.SkillCategoryRepository;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.labcabrera.rolemaster.core.service.character.processor.CharacterPostProcessorService;
import org.labcabrera.rolemaster.core.table.skill.SkillCategoryBonusTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CharacterCreationService {

	private static final String MSG_INVALID_WEAPON_SIZE = "Invalid request weapon category order count. Expected: %s, received: %s";

	@Autowired
	private AttributeCreationService attributeCreationService;

	@Autowired
	private CharacterInfoRepository repository;

	@Autowired
	private CharacterPostProcessorService postProcessorService;

	@Autowired
	private SkillCategoryBonusTable skillCategoryBonusTable;

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
			.level(0)
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
			.switchIfEmpty(Mono.error(new BadRequestException("Race " + request.getRaceId() + " not found")))
			.map(tuple -> {
				tuple.getT1().setRace(tuple.getT2());
				return tuple.getT1();
			})
			.zipWith(professionRepository.findById(request.getProfessionId()))
			.switchIfEmpty(Mono.error(new BadRequestException("Profession " + request.getProfessionId() + " not found")))
			.map(tuple -> {
				tuple.getT1().setProfession(tuple.getT2());
				return tuple.getT1();
			})
			.flatMap(ctx -> {
				return skillCategoryRepository.findAll(Sort.by("id"))
					.collectList()
					.doOnNext(ctx::setSkillCategories)
					.map(e -> ctx);
			})
			.flatMap(ctx -> {
				return skillRepository.findSkillsOnNewCharacter()
					.collectList()
					.doOnNext(ctx::setSkills)
					.map(e -> ctx);
			})
			.map(ctx -> loadAttributes(ctx, request))
			.map(this::loadSkillCategories)
			.map(ctx -> loadSkillCategoryWeapons(ctx, request))
			.map(this::loadSkills)
			.map(e -> {
				postProcessorService.apply(e.getCharacter());
				return e;
			})
			.map(CharacterModificationContext::getCharacter)
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

	private CharacterModificationContext loadSkillCategories(CharacterModificationContext context) {
		if (context.getSkillCategories() == null || context.getSkillCategories().isEmpty()) {
			log.warn("Undefined categories");
		}

		CharacterInfo character = context.getCharacter();
		Race race = context.getRace();
		Profession profession = context.getProfession();

		context.getSkillCategories().stream().forEach(category -> {
			String categoryId = category.getId();
			int adolescenceRank = race.getAdolescenseSkillCategoryRanks().getOrDefault(categoryId, 0);
			int bonusProfession = profession.getSkillCategoryBonus().getOrDefault(categoryId, 0);
			int bonusAttribute = getAttributeBonus(category, character);
			int bonusRanks = skillCategoryBonusTable.getBonus(adolescenceRank);
			CharacterSkillCategory characterSkillCategory = CharacterSkillCategory.builder()
				.categoryId(category.getId())
				.developmentCost(profession.getSkillCategoryDevelopmentCost().getOrDefault(categoryId, new ArrayList<>()))
				.attributes(category.getAttributeBonus())
				.group(category.getGroup())
				.build();
			characterSkillCategory.getRanks().put(RankType.ADOLESCENCE, adolescenceRank);
			characterSkillCategory.getBonus().put(BonusType.RANK, bonusRanks);
			characterSkillCategory.getBonus().put(BonusType.PROFESSION, bonusProfession);
			characterSkillCategory.getBonus().put(BonusType.ATTRIBUTE, bonusAttribute);

			character.getSkillCategories().add(characterSkillCategory);
		});
		return context;
	}

	private CharacterModificationContext loadSkills(CharacterModificationContext context) {
		context.getSkills().stream().forEach(skill -> {
			String categoryId = skill.getCategoryId();
			CharacterSkillCategory category = context.getCharacter().getSkillCategory(categoryId)
				.orElseThrow(() -> new BadRequestException("Invalid skill category " + categoryId));
			CharacterSkill cs = CharacterSkill.builder()
				.skillId(skill.getId())
				.categoryId(skill.getCategoryId())
				.group(category.getGroup())
				.developmentCost(category.getDevelopmentCost())
				.attributes(skill.getAttributeBonus())
				.build();
			cs.getBonus().put(BonusType.SKILL_SPECIAL, skill.getSkillBonus());
			context.getCharacter().getSkills().add(cs);
		});
		return context;
	}

	private CharacterModificationContext loadSkillCategoryWeapons(CharacterModificationContext context, CharacterCreationRequest request) {
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
			int raceBonus = 0;
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
}
