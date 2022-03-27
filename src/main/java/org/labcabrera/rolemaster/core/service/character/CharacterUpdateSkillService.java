package org.labcabrera.rolemaster.core.service.character;

import org.apache.commons.lang3.mutable.MutableInt;
import org.labcabrera.rolemaster.core.dto.SkillUpgradeRequest;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.service.character.processor.CharacterPostProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CharacterUpdateSkillService {

	private static final String ERR_MISSING_CATEGORY_ID = "Missing skill category %s";
	private static final String ERR_MISSING_SKILL_ID = "Missing skill %s";
	private static final String ERR_INVALID_LEVEL_COUNT = "Upgraded levels exceds the development value";
	private static final String ERR_EXCEDED_DEV_POINTS = "Request development points exceds the remaining value";

	@Autowired
	private CharacterInfoRepository repository;

	@Autowired
	private CharacterPostProcessorService postProcessorService;

	public Mono<CharacterInfo> updateRanks(String characterId, SkillUpgradeRequest request) {
		return repository.findById(characterId)
			.map(e -> update(e, request))
			.map(postProcessorService::apply)
			.flatMap(repository::save);
	}

	private CharacterInfo update(CharacterInfo character, SkillUpgradeRequest request) {
		final MutableInt cost = new MutableInt(0);
		upgradeSkillCategories(character, request, cost);
		upgradeSkills(character, request, cost);
		int usedPoints = character.getDevelopmentPoints().getUsedPoints() + cost.getValue();
		if (usedPoints > character.getDevelopmentPoints().getTotalPoints()) {
			throw new BadRequestException(ERR_EXCEDED_DEV_POINTS);
		}
		character.getDevelopmentPoints().setUsedPoints(usedPoints);
		return character;
	}

	private void upgradeSkillCategories(CharacterInfo character, SkillUpgradeRequest request, MutableInt cost) {
		request.getCategoryRanks().keySet().stream().forEach(categoryId -> {
			CharacterSkillCategory category = character.getSkillCategories().stream()
				.filter(e -> e.getCategoryId().equals(categoryId))
				.findFirst().orElseThrow(() -> new BadRequestException(String.format(ERR_MISSING_CATEGORY_ID, categoryId)));
			int levels = request.getCategoryRanks().get(categoryId);
			if (levels > 0) {
				if (levels > category.getDevelopmentCost().size()) {
					throw new BadRequestException(ERR_INVALID_LEVEL_COUNT);
				}
				for (int i = 0; i < levels; i++) {
					cost.add(category.getDevelopmentCost().get(i));
				}
				category.getRanks().put(RankType.DEVELOPMENT, levels);
			}
		});
	}

	private void upgradeSkills(CharacterInfo character, SkillUpgradeRequest request, MutableInt cost) {
		request.getSkillRanks().keySet().stream().forEach(skillId -> {
			CharacterSkill skill = character.getSkills().stream()
				.filter(e -> e.getSkillId().equals(skillId))
				.findFirst().orElseThrow(() -> new BadRequestException(String.format(ERR_MISSING_SKILL_ID, skillId)));
			int levels = request.getSkillRanks().get(skillId);
			if (levels > 0) {
				if (levels > skill.getDevelopmentCost().size()) {
					throw new BadRequestException(ERR_INVALID_LEVEL_COUNT);
				}
				for (int i = 0; i < levels; i++) {
					cost.add(skill.getDevelopmentCost().get(i));
				}
				skill.setUpgradedRanks(skill.getUpgradedRanks() + levels);
			}
		});
	}

}
