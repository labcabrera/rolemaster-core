package org.labcabrera.rolemaster.core.services.commons.character.creation.processor;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterResistance;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.ResistanceBonusType;
import org.labcabrera.rolemaster.core.model.character.ResistanceType;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(CharacterUpdatePostProcessor.Orders.RESISTANCE)
public class CharacterResistancePostProcessor implements CharacterUpdatePostProcessor {

	@Override
	public void accept(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		Race race = context.getRace();
		for (ResistanceType r : ResistanceType.values()) {
			int raceBonus = race.getResistanceBonus().getOrDefault(r, 0);
			CharacterResistance cr = character.getResistances().get(r);
			if (cr == null) {
				cr = new CharacterResistance();
				character.getResistances().put(r, cr);
			}
			cr.getBonus().put(ResistanceBonusType.RACE, raceBonus);
			character.getResistances().put(r, cr);
		}
	}

}
