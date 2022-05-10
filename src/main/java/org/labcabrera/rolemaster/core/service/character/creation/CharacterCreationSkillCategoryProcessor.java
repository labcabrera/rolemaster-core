package org.labcabrera.rolemaster.core.service.character.creation;

import java.util.ArrayList;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.BonusType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.skill.SkillCategory;
import org.springframework.stereotype.Component;

@Component
public class CharacterCreationSkillCategoryProcessor {

	public CharacterModificationContext loadSkillCategories(CharacterModificationContext context) {
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

	private Integer getAttributeBonus(SkillCategory category, CharacterInfo characterInfo) {
		int result = 0;
		for (AttributeType at : category.getAttributeBonus()) {
			result += characterInfo.getAttributes().get(at).getTotalBonus();
		}
		return result;
	}
}
