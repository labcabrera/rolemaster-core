package org.labcabrera.rolemaster.core.service.character;

import org.apache.commons.lang3.mutable.MutableInt;
import org.labcabrera.rolemaster.core.dto.SkillUpgradeRequest;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CharacterUpdateSkillService {

	private static final String ERR_MISSING_CATEGORYID = "Missing skill category %s";
	private static final String ERR_INVALID_LEVEL_COUNT = "Upgraded levels exceds the development value";
	private static final String ERR_EXCEDED_DEV_POINTS = "Request development points exceds the remaining value";

	@Autowired
	private CharacterInfoRepository repository;

	public Mono<CharacterInfo> updateRanks(String characterId, SkillUpgradeRequest request) {
		return repository.findById(characterId)
			.map(e -> update(e, request))
			.flatMap(repository::save);
	}

	private CharacterInfo update(CharacterInfo character, SkillUpgradeRequest request) {
		final MutableInt cost = new MutableInt(0);
		request.getCategoryRanks().keySet().stream().forEach(categoryId -> {
			CharacterSkillCategory category = character.getSkillCategories().stream()
				.filter(e -> e.getCategoryId().equals(categoryId))
				.findFirst().orElseThrow(() -> new BadRequestException(String.format(ERR_MISSING_CATEGORYID, categoryId)));

			int levels = request.getCategoryRanks().get(categoryId);
			if (levels > 0) {
				if (levels > category.getDevelopmentCost().size()) {
					throw new BadRequestException(ERR_INVALID_LEVEL_COUNT);
				}
				for (int i = 0; i < levels; i++) {
					cost.add(category.getDevelopmentCost().get(i));
				}
				category.setUpgradedRanks(category.getUpgradedRanks() + levels);
			}
		});
		int remainingPoints = character.getDevelopmentPoints().getRemainingPoints() - cost.getValue();
		if (remainingPoints < 0) {
			throw new BadRequestException(ERR_EXCEDED_DEV_POINTS);
		}
		character.getDevelopmentPoints().setRemainingPoints(remainingPoints);
		return character;
	}
}
