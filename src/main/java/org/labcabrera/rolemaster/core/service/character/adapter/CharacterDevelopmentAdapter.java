package org.labcabrera.rolemaster.core.service.character.adapter;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterDevelopment;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.springframework.stereotype.Component;

@Component
public class CharacterDevelopmentAdapter implements CharacterAdapter {

	@Override
	public CharacterModificationContext apply(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();

		int tmp = 0;
		tmp += character.getAttributes().get(AttributeType.AGILITY).getCurrentValue();
		tmp += character.getAttributes().get(AttributeType.CONSTITUTION).getCurrentValue();
		tmp += character.getAttributes().get(AttributeType.MEMORY).getCurrentValue();
		tmp += character.getAttributes().get(AttributeType.REASONING).getCurrentValue();
		tmp += character.getAttributes().get(AttributeType.SELF_DISCIPLINE).getCurrentValue();

		int dp = tmp / 5;
		CharacterDevelopment cd = CharacterDevelopment.builder()
			.totalPoints(dp)
			.remainingPoints(dp)
			.build();

		character.setDevelopmentPoints(cd);

		return context;
	}

}
