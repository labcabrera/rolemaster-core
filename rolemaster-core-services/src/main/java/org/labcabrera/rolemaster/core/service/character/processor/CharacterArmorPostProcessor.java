package org.labcabrera.rolemaster.core.service.character.processor;

import org.labcabrera.rolemaster.core.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.table.armor.ArmorModifier;
import org.labcabrera.rolemaster.core.table.armor.ArmorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(CharacterPostProcessor.Orders.DB)
public class CharacterArmorPostProcessor implements CharacterPostProcessor {

	@Autowired
	private ArmorService armorService;

	@Override
	public void accept(CharacterInfo character) {
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
