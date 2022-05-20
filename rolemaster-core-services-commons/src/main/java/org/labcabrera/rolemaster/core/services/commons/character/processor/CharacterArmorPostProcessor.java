package org.labcabrera.rolemaster.core.services.commons.character.processor;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.labcabrera.rolemaster.core.table.rmss.armor.ArmorModifier;
import org.labcabrera.rolemaster.core.table.rmss.armor.ArmorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(CharacterUpdatePostProcessor.Orders.DB)
public class CharacterArmorPostProcessor implements CharacterUpdatePostProcessor {

	@Autowired
	private ArmorService armorService;

	@Override
	public void accept(CharacterModificationContext context) {
		//TODO RMU integration

		CharacterInfo character = context.getCharacter();
		int equippedArmor = character.getArmor().getArmor();
		ArmorModifier armorModifier = armorService.getArmorModifier(equippedArmor);

		int mmMin = armorModifier.getMmMin();
		int mmMax = armorModifier.getMmMax();
		int skillIdBonus = armorModifier.getSkillId() != null ? getSkillBonus(character, armorModifier.getSkillId()) : 0;

		int mmPenalty = Integer.min(mmMin, mmMax + skillIdBonus);

		character.getArmor().setArmorPenalty(mmPenalty);
		character.getArmor().setArmorQuPenalty(armorModifier.getQuicknessPenalty());

	}

	private int getSkillBonus(CharacterInfo character, String skillId) {
		return character.getSkill(skillId)
			.orElseThrow(() -> new DataConsistenceException("Missing skill " + skillId))
			.getTotalBonus();
	}

}
