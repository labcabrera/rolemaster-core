package org.labcabrera.rolemaster.core.service.character;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.dto.TrainingPackageCategorySelection;
import org.labcabrera.rolemaster.core.dto.TrainingPackageSkillSelection;
import org.labcabrera.rolemaster.core.dto.TrainingPackageUpgrade;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.character.TrainingPackage;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.skill.Skill;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.SkillRepository;
import org.labcabrera.rolemaster.core.repository.TrainingPackageRepository;
import org.labcabrera.rolemaster.core.service.Messages.Errors;
import org.labcabrera.rolemaster.core.service.character.processor.CharacterPostProcessorService;
import org.labcabrera.rolemaster.core.service.security.WriteAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@Validated
public class CharacterAddTrainingPackageServiceImpl implements CharacterAddTrainingPackageService {

	@Autowired
	private CharacterInfoService characterInfoService;

	@Autowired
	private CharacterInfoRepository characterInfoRepository;

	@Autowired
	private TrainingPackageRepository trainingPackageRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private CharacterPostProcessorService postProcessorService;

	@Autowired
	private WriteAuthorizationFilter writeFilter;

	@Override
	public Mono<CharacterInfo> upgrade(JwtAuthenticationToken auth, String characterId, TrainingPackageUpgrade request) {
		return characterInfoService.findById(auth, characterId)
			.map(c -> writeFilter.apply(auth, c))
			.zipWith(trainingPackageRepository.findById(request.getTrainingPackageId()))
			.switchIfEmpty(Mono.error(() -> new BadRequestException(Errors.trainingPackageNotFound(request.getTrainingPackageId()))))
			.map(this::validate)
			.flatMap(this::upgradeFixedRanks)
			.map(pair -> upgradeSelectableCategories(pair, request))
			.flatMap(pair -> upgradeSelectableSkills(pair, request))
			.map(this::applyPackage)
			.map(Tuple2::getT1)
			.map(postProcessorService)
			.flatMap(characterInfoRepository::save);
	}

	private Tuple2<CharacterInfo, TrainingPackage> validate(Tuple2<CharacterInfo, TrainingPackage> pair) {
		CharacterInfo character = pair.getT1();
		TrainingPackage tp = pair.getT2();
		if (character.getTrainingPackages().containsKey(tp.getId())) {
			throw new BadRequestException("Training package already adquired.");
		}
		if (!tp.getCostByProfession().containsKey(character.getProfessionId())) {
			throw new BadRequestException("Training package is not available for profession " + character.getProfessionId());
		}
		int cost = tp.getCostByProfession().get(character.getProfessionId());
		int usedPointsAfter = cost + character.getDevelopmentPoints().getUsedPoints();
		if (usedPointsAfter > character.getDevelopmentPoints().getTotalPoints()) {
			throw new BadRequestException("Not available development points");
		}
		//TODO comprobar vocacionales/profesionales
		//TODO comprobar numero de rangos
		return pair;
	}

	private Mono<Tuple2<CharacterInfo, TrainingPackage>> upgradeFixedRanks(Tuple2<CharacterInfo, TrainingPackage> pair) {
		CharacterInfo character = pair.getT1();
		TrainingPackage tp = pair.getT2();
		tp.getFixedSkillCategories().entrySet().stream().forEach(e -> addSkillCategoryRanks(character, e.getKey(), e.getValue()));
		List<String> newSkills = readNewFixedSkills(character, tp);
		if (newSkills.isEmpty()) {
			tp.getFixedSkills().entrySet().stream().forEach(e -> addSkillRanks(character, e.getKey(), e.getValue()));
			return Mono.just(pair);
		}
		return Mono.just(pair)
			.zipWith(skillRepository.findByIds(newSkills).collectList())
			.map(tmp -> {
				List<Skill> skills = tmp.getT2();
				if (skills.size() != newSkills.size()) {
					throw new DataConsistenceException("Unexpected skills readed. Expected " + newSkills);
				}
				skills.stream().forEach(s -> {
					Integer ranks = tp.getFixedSkills().get(s.getId());
					addNewSkill(character, s, ranks);
				});
				return tmp.getT1();
			});
	}

	private Tuple2<CharacterInfo, TrainingPackage> upgradeSelectableCategories(Tuple2<CharacterInfo, TrainingPackage> pair,
		TrainingPackageUpgrade request) {
		CharacterInfo character = pair.getT1();
		for (TrainingPackageCategorySelection selection : request.getCategorySelection()) {
			addSkillCategoryRanks(character, selection.getCategoryId(), selection.getRanks());
		}
		return pair;
	}

	private Mono<Tuple2<CharacterInfo, TrainingPackage>> upgradeSelectableSkills(Tuple2<CharacterInfo, TrainingPackage> pair,
		TrainingPackageUpgrade request) {
		CharacterInfo character = pair.getT1();

		List<String> existingSkills = request.getSkillSelection().stream().map(TrainingPackageSkillSelection::getSkillId)
			.filter(e -> character.getSkill(e).isPresent())
			.toList();

		existingSkills.stream().forEach(skillId -> {
			int ranks = request.getSkillSelection().stream()
				.filter(e -> skillId.equals(e.getSkillId()))
				.findFirst().orElseThrow(() -> new DataConsistenceException("Unexpected skill " + skillId + "  not found."))
				.getRanks();
			addSkillRanks(character, skillId, ranks);
		});

		List<String> newSkills = request.getSkillSelection().stream().map(TrainingPackageSkillSelection::getSkillId)
			.filter(e -> character.getSkill(e).isEmpty())
			.toList();

		if (newSkills.isEmpty()) {
			return Mono.just(pair);
		}

		return Mono.just(pair)
			.zipWith(skillRepository.findByIds(newSkills).collectList())
			.map(tmp -> {
				List<Skill> skills = tmp.getT2();
				if (skills.size() != newSkills.size()) {
					throw new DataConsistenceException("Unexpected skills readed. Expected " + newSkills);
				}
				skills.stream().forEach(s -> {
					Integer ranks = request.getSkillSelection().stream()
						.filter(e -> e.getSkillId().equals(s.getId())).findFirst().get()
						.getRanks();
					addNewSkill(character, s, ranks);
				});
				return tmp.getT1();
			});
	}

	private void addNewSkill(CharacterInfo character, Skill s, Integer ranks) {
		String skillId = s.getId();
		String skillCategoryId = s.getCategoryId();
		CharacterSkillCategory csc = character.getSkillCategory(skillCategoryId)
			.orElseThrow(() -> new DataConsistenceException("Missing skill category"));
		CharacterSkill cs = CharacterSkill.builder()
			.skillId(skillId)
			.categoryId(skillCategoryId)
			.attributes(csc.getAttributes())
			.developmentCost(csc.getDevelopmentCost())
			.group(csc.getGroup())
			.progressionType(s.getProgressionType())
			.build();
		cs.getRanks().put(RankType.TRAINING_PACKAGE, ranks);
		character.getSkills().add(cs);
	}

	private Tuple2<CharacterInfo, TrainingPackage> applyPackage(Tuple2<CharacterInfo, TrainingPackage> pair) {
		CharacterInfo character = pair.getT1();
		TrainingPackage tp = pair.getT2();
		int cost = tp.getCostByProfession().get(character.getProfessionId());
		int devPoints = cost + character.getDevelopmentPoints().getUsedPoints();
		character.getDevelopmentPoints().getDevelopmentTrainingPackages().put(tp.getId(), cost);
		character.getDevelopmentPoints().setUsedPoints(devPoints);
		character.getTrainingPackages().put(tp.getId(), tp.getType());
		return pair;
	}

	private void addSkillCategoryRanks(CharacterInfo character, String categoryId, int ranks) {
		CharacterSkillCategory category = character.getSkillCategory(categoryId)
			.orElseThrow(() -> new BadRequestException(Errors.characterMissingSkillCategory(categoryId)));
		int newRanks = ranks + category.getRanks().getOrDefault(RankType.TRAINING_PACKAGE, 0);
		category.getRanks().put(RankType.TRAINING_PACKAGE, newRanks);
	}

	private void addSkillRanks(CharacterInfo character, String skillId, int ranks) {
		CharacterSkill skill = character.getSkill(skillId)
			.orElseThrow(() -> new BadRequestException(Errors.characterMissingSkillCategory(skillId)));
		int newRanks = ranks + skill.getRanks().getOrDefault(RankType.TRAINING_PACKAGE, 0);
		skill.getRanks().put(RankType.TRAINING_PACKAGE, newRanks);
	}

	private List<String> readNewFixedSkills(CharacterInfo character, TrainingPackage tp) {
		List<String> result = new ArrayList<>();
		for (String skillId : tp.getFixedSkills().keySet()) {
			if (character.getSkill(skillId).isEmpty()) {
				result.add(skillId);
			}
		}
		return result;
	}

}
