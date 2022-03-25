package org.labcabrera.rolemaster.core.service.character.creation;

import java.util.ArrayList;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.SkillCategory;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.table.skill.SkillCategoryBonusTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CharacterCreationSkillCategoryService {

	@Autowired
	private SkillCategoryBonusTable skillCategoryBonusTable;

	public CharacterModificationContext initialize(CharacterModificationContext context) {
		if (context.getSkillCategories() == null || context.getSkillCategories().isEmpty()) {
			log.warn("Undefined categories");
		}

		CharacterInfo character = context.getCharacter();
		Race race = context.getRace();
		Profession profession = context.getProfession();

		context.getSkillCategories().stream().forEach(category -> {
			String categoryId = category.getId();

			int adolescenceRank = race.getAdolescenseSkillCategoryRanks().getOrDefault(categoryId, 0);
			int professionBonus = profession.getSkillCategoryBonus().getOrDefault(categoryId, 0);
			int totalRanks = adolescenceRank;

			int attributeBonus = getAttributeBonus(category, character);
			int rankBonus = skillCategoryBonusTable.getBonus(totalRanks);
			int totalBonus = professionBonus + attributeBonus + rankBonus;
			CharacterSkillCategory characterSkillCategory = CharacterSkillCategory.builder()
				.categoryId(category.getId())
				.developmentCost(profession.getSkillCategoryDevelopmentCost().getOrDefault(categoryId, new ArrayList<>()))
				.adolescenceRanks(adolescenceRank)
				.totalRanks(totalRanks)
				.rankBonus(rankBonus)
				.attributeBonus(attributeBonus)
				.professionBonus(professionBonus)
				.totalBonus(totalBonus)
				.build();
			character.getSkillCategories().add(characterSkillCategory);
		});
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
