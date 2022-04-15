package org.labcabrera.rolemaster.core.service.character.processor;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.BonusType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.table.skill.SkillStandarBonusTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(CharacterPostProcessor.Orders.SKILL)
@Slf4j
public class CharacterSkillProcessor implements CharacterPostProcessor {

	@Autowired
	private SkillStandarBonusTable standarBonusTable;

	@Override
	public void accept(CharacterInfo character) {
		log.debug("Processing character {}", character.getName());
		character.getSkills().stream().forEach(characterSkill -> {
			characterSkill.getBonus().put(BonusType.ATTRIBUTE, getBonusAttribute(characterSkill, character));
			characterSkill.getBonus().put(BonusType.CATEGORY, getBonusCategory(characterSkill, character));
			characterSkill.getBonus().put(BonusType.RANK, getBonusRank(characterSkill));
		});
	}

	private int getBonusAttribute(CharacterSkill characterSkill, CharacterInfo character) {
		int bonusAttribute = 0;
		for (AttributeType i : characterSkill.getAttributes()) {
			bonusAttribute += character.getAttributes().get(i).getTotalBonus();
		}
		return bonusAttribute;
	}

	private int getBonusCategory(CharacterSkill characterSkill, CharacterInfo character) {
		return character.getSkillCategory(characterSkill.getCategoryId()).get().getTotalBonus();
	}

	private int getBonusRank(CharacterSkill skill) {
		//TODO ver los casos en los que no se usa la tabla estandar
		return standarBonusTable.apply(skill.getTotalRanks());
	}

}
