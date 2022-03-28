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

			int bonusAttribute = 0;
			for (AttributeType i : characterSkill.getAttributes()) {
				bonusAttribute += character.getAttributes().get(i).getTotalBonus();
			}

			int bonusRank = getBonusRank(characterSkill);

			characterSkill.getBonus().put(BonusType.RANK, bonusAttribute);
			characterSkill.getBonus().put(BonusType.ATTRIBUTE, bonusRank);
		});
	}

	private int getBonusRank(CharacterSkill skill) {
		//TODO ver los casos en los que no se usa la tabla estandar
		return standarBonusTable.apply(skill.getTotalRanks());
	}

}
