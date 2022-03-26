package org.labcabrera.rolemaster.core.service.character.processor;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.BonusType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.table.skill.SkillCategoryBonusTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterSkillCategoryPostprocessor implements CharacterPostProcessor {

	@Autowired
	private SkillCategoryBonusTable bonusTable;

	@Override
	public void accept(CharacterInfo character) {
		character.getSkillCategories().stream().forEach(category -> {
			category.getBonus().put(BonusType.ATTRIBUTE, getCategoryBonus(category, character));
			category.getBonus().put(BonusType.RANK, bonusTable.getBonus(category.getTotalRanks()));
		});
	}

	private int getCategoryBonus(CharacterSkillCategory category, CharacterInfo character) {
		int result = 0;
		for (AttributeType at : category.getAttributes()) {
			result += character.getAttributes().get(at).getTotalBonus();
		}
		return result;
	}

}
