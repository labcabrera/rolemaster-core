package org.labcabrera.rolemaster.core.service.table.skill;

import java.util.List;

import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
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
			return categoryTable.applyAsInt(ranks);
		case STANDARD:
			return standardTable.applyAsInt(ranks);
		case COMBINED:
			return combinedTable.applyAsInt(ranks);
		case LIMITED:
			return limitedTable.applyAsInt(ranks);
		case SPECIAL, RACE_POWER_POINTS, RACE_BODY_DEVELOPMENT:
			return specialTable.apply(ranks, mask);
		default:
			throw new DataConsistenceException("Invalid progresssion type " + progressionType);
		}
	}
}
