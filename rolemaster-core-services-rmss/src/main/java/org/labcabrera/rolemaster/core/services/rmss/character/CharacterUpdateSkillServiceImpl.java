package org.labcabrera.rolemaster.core.services.rmss.character;

import java.util.List;

import org.labcabrera.rolemaster.core.dto.SkillUpgrade;
import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.HasRanks;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.exception.NotFoundException;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.services.character.CharacterInfoService;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdateSkillService;
import org.labcabrera.rolemaster.core.services.commons.Messages.Errors;
import org.labcabrera.rolemaster.core.services.commons.MetadataModificationUpdater;
import org.labcabrera.rolemaster.core.services.commons.security.WriteAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;

@Service
@Validated
public class CharacterUpdateSkillServiceImpl extends AbstractCharacterModificationService implements CharacterUpdateSkillService {

	private static final String ERR_MISSING_CATEGORY_ID = "Missing skill category %s";
	private static final String ERR_MISSING_SKILL_ID = "Missing skill %s";

	@Autowired
	private CharacterInfoService characterInfoService;

	@Autowired
	private MetadataModificationUpdater metadataModificationDateUpdater;

	@Autowired
	private CharacterInfoRepository repository;

	@Autowired
	private WriteAuthorizationFilter writeAuthorizationFilter;

	@Override
	public Mono<CharacterInfo> updateRanks(JwtAuthenticationToken auth, String characterId, SkillUpgrade request) {
		return characterInfoService.findById(auth, characterId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterNotFound(characterId))))
			.map(c -> writeAuthorizationFilter.apply(auth, c))
			.map(character -> upgradeSkillCategories(character, request))
			.map(character -> upgradeSkills(character, request))
			.map(this::calculateDevelopmentCost)
			.flatMap(contextLoader::apply)
			.map(this::applyPostProcessors)
			.map(CharacterModificationContext::getCharacter)
			.map(metadataModificationDateUpdater::apply)
			.flatMap(repository::save);
	}

	private CharacterInfo upgradeSkillCategories(CharacterInfo character, SkillUpgrade request) {
		request.getCategoryRanks().keySet().stream().forEach(categoryId -> {
			CharacterSkillCategory category = character.getSkillCategory(categoryId)
				.orElseThrow(() -> new BadRequestException(String.format(ERR_MISSING_CATEGORY_ID, categoryId)));
			List<Integer> devCost = category.getDevelopmentCost();
			int requestedLevels = request.getCategoryRanks().get(categoryId);
			int currentDevLevel = category.getRanks().getOrDefault(RankType.DEVELOPMENT, 0);
			int newDevLevel = requestedLevels + currentDevLevel;
			if (newDevLevel < 0 || newDevLevel > devCost.size()) {
				throw new BadRequestException("Invalid development level: " + newDevLevel);
			}
			category.getRanks().put(RankType.DEVELOPMENT, newDevLevel);
		});
		return character;
	}

	private CharacterInfo upgradeSkills(CharacterInfo character, SkillUpgrade request) {
		request.getSkillRanks().keySet().stream().forEach(skillId -> {
			CharacterSkill skill = character.getSkill(skillId)
				.orElseThrow(() -> new BadRequestException(String.format(ERR_MISSING_SKILL_ID, skillId)));
			List<Integer> devCost = skill.getDevelopmentCost();
			int requestedLevels = request.getSkillRanks().get(skillId);
			int currentDevLevel = skill.getRanks().getOrDefault(RankType.DEVELOPMENT, 0);
			int newDevLevel = requestedLevels + currentDevLevel;
			if (newDevLevel < 0 || newDevLevel > devCost.size()) {
				throw new BadRequestException("Invalid development level: " + newDevLevel);
			}
			skill.getRanks().put(RankType.DEVELOPMENT, newDevLevel);
		});
		return character;
	}

	private CharacterInfo calculateDevelopmentCost(CharacterInfo character) {
		int devCost = calculateCost(character.getSkillCategories())
			+ calculateCost(character.getSkills())
			+ calculateTrainingPackageCost(character);
		if (devCost > character.getDevelopmentPoints().getTotalPoints()) {
			throw new BadRequestException("Invalid dev cost TODO");
		}
		character.getDevelopmentPoints().setUsedPoints(devCost);
		return character;
	}

	private int calculateCost(List<? extends HasRanks> list) {
		return list.stream()
			.filter(e -> e.getRanks().getOrDefault(RankType.DEVELOPMENT, 0) > 0)
			.map(this::getCost)
			.reduce(0, (a, b) -> a + b);
	}

	private int calculateTrainingPackageCost(CharacterInfo character) {
		return character.getDevelopmentPoints().getDevelopmentTrainingPackages().values().stream()
			.reduce(0, (a, b) -> a + b);
	}

	private int getCost(HasRanks category) {
		int result = 0;
		for (int i = 0; i < category.getRanks().get(RankType.DEVELOPMENT); i++) {
			result += category.getDevelopmentCost().get(i);
		}
		return result;
	}

}
