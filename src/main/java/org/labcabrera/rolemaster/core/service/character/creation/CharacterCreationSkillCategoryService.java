package org.labcabrera.rolemaster.core.service.character.creation;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CharacterCreationSkillCategoryService {

	public CharacterModificationContext initialize(CharacterModificationContext context) {
		if (context.getSkillCategories() == null || context.getSkillCategories().isEmpty()) {
			log.warn("Undefined categories");
		}
		CharacterInfo character = context.getCharacter();
		context.getSkillCategories().stream().forEach(category -> {
			CharacterSkillCategory characterSkillCategory = CharacterSkillCategory.builder()
				.categoryId(category.getId())
				.build();
			character.getSkillCategories().add(characterSkillCategory);
		});
		Race race = context.getRace();
		loadRaceSkillCategories(character, race);
		return context;
	}

	private void loadRaceSkillCategories(CharacterInfo character, Race race) {
		log.debug("Loading race {} skill categories", race.getId());
		race.getAdolescenseSkillCategoryRanks().keySet().stream().forEach(categoryId -> {
			Integer rank = race.getAdolescenseSkillCategoryRanks().get(categoryId);
			character.getSkillCategories().stream()
				.filter(e -> e.getCategoryId().equals(categoryId))
				.findFirst().orElseThrow()
				.setAdolescenceRank(rank);
		});
	}

}
