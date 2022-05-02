package org.labcabrera.rolemaster.core.table.skill;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.skill.SkillProgressionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillRankBonusService {

	@Autowired
	private SkillRankBonusTable bonusTable;

	public int getBonus(CharacterSkillCategory category, CharacterInfo character) {
		int ranks = category.getTotalRanks();
		return bonusTable.getBonus(category.getProgressionType(), ranks, null);
	}

	public int getBonus(CharacterSkill characterSkill, CharacterInfo character) {
		List<Integer> mask = null;
		if (characterSkill.getProgressionType() == SkillProgressionType.RACE_BODY_DEVELOPMENT) {
			mask = character.getBodyDevelopmentProgression();
		}
		else if (characterSkill.getProgressionType() == SkillProgressionType.RACE_POWER_POINTS) {
			mask = character.getPowerPointProgression();
		}
		int ranks = characterSkill.getTotalRanks();
		return bonusTable.getBonus(characterSkill.getProgressionType(), ranks, mask);
	}

}
