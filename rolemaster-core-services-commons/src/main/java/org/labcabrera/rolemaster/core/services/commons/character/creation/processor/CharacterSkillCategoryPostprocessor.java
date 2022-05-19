package org.labcabrera.rolemaster.core.services.commons.character.creation.processor;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.BonusType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.labcabrera.rolemaster.core.table.rmss.skill.SkillRankBonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(CharacterUpdatePostProcessor.Orders.SKILL_CATEGORY)
public class CharacterSkillCategoryPostprocessor implements CharacterUpdatePostProcessor {

	@Autowired
	private SkillRankBonusService rankBonusService;

	@Override
	public void accept(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		if (character.getVersion() != RolemasterVersion.RMSS) {
			return;
		}

		character.getSkillCategories().stream().forEach(category -> {
			int rankBonus = rankBonusService.getBonus(category);
			category.getBonus().put(BonusType.ATTRIBUTE, getCategoryBonus(category, character));
			category.getBonus().put(BonusType.RANK, rankBonus);
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
