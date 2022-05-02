package org.labcabrera.rolemaster.core.table.skill;

import java.util.List;

import org.labcabrera.rolemaster.core.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.skill.SkillProgressionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class SkillRankBonusTable {

	@Autowired
	private SkillCategoryBonusTable categoryTable;

	@Autowired
	private SkillStandardBonusTable standardTable;

	@Autowired
	private SkillCombinedBonusTable combinedTable;

	@Autowired
	private SkillLimitedBonusTable limitedTable;

	@Autowired
	private SkillSpecialBonusTable specialTable;

	public int getBonus(SkillProgressionType progressionType, int ranks, List<Integer> mask) {
		switch (progressionType) {
		case NONE:
			return 0;
		case CATEGORY:
			return categoryTable.apply(ranks);
		case STANDARD:
			return standardTable.apply(ranks);
		case COMBINED:
			return combinedTable.apply(ranks);
		case LIMITED:
			return limitedTable.apply(ranks);
		case SPECIAL:
		case RACE_POWER_POINTS:
		case RACE_BODY_DEVELOPMENT:
			return specialTable.apply(ranks, mask);
		default:
			throw new DataConsistenceException("Invalid progresssion type " + progressionType);
		}
	}
}
