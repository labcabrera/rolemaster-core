package org.labcabrera.rolemaster.core.services.rmss.character;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.BaseMovementRate;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.services.character.CharacterBaseMovementRateService;
import org.labcabrera.rolemaster.core.table.rmss.character.StrideChartRmssTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterBaseMovementRateRmssService implements CharacterBaseMovementRateService {

	@Autowired
	private StrideChartRmssTable strideChartTable;

	@Override
	public BaseMovementRate getBaseMovementRate(CharacterInfo character, Race race) {
		int qu = character.getAttributes().get(AttributeType.QUICKNESS).getTotalBonus();
		int baseValue = 50;
		int attributeBonus = 3 * qu;
		int strideBonus = strideChartTable.getValue(character.getHeight());
		double bmr = baseValue + attributeBonus + strideBonus;
		return BaseMovementRate.builder()
			.bmr(bmr)
			.build();
	}

	@Override
	public List<RolemasterVersion> compatibleVersions() {
		return Arrays.asList(RolemasterVersion.RMSS);
	}

}
