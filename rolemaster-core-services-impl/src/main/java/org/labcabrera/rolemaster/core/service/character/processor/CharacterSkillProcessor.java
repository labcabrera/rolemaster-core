package org.labcabrera.rolemaster.core.service.character.processor;

import org.labcabrera.rolemaster.core.model.character.BonusType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.service.Messages.Errors;
import org.labcabrera.rolemaster.core.service.character.CharacterUpdatePostProcessor;
import org.labcabrera.rolemaster.core.table.skill.SkillRankBonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(CharacterUpdatePostProcessor.Orders.SKILL)
@Slf4j
public class CharacterSkillProcessor implements CharacterUpdatePostProcessor {

	@Autowired
	private SkillRankBonusService rankBonusService;

	@Override
	public void accept(CharacterInfo character) {
		log.debug("Processing character {}", character.getName());
		character.getSkills().stream().forEach(characterSkill -> {
			int rankBonus = rankBonusService.getBonus(characterSkill, character);
			characterSkill.getBonus().put(BonusType.CATEGORY, getBonusCategory(characterSkill, character));
			characterSkill.getBonus().put(BonusType.RANK, rankBonus);
		});
	}

	private int getBonusCategory(CharacterSkill characterSkill, CharacterInfo character) {
		String categoryId = characterSkill.getCategoryId();
		return character.getSkillCategory(categoryId)
			.orElseThrow(() -> new DataConsistenceException(Errors.characterMissingSkillCategory(categoryId)))
			.getTotalBonus();
	}

}
