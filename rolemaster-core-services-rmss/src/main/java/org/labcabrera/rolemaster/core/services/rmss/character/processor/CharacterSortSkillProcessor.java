package org.labcabrera.rolemaster.core.services.rmss.character.processor;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(CharacterUpdatePostProcessor.Orders.SORT_SKILLS)
public class CharacterSortSkillProcessor implements CharacterUpdatePostProcessor {

	@Override
	public void accept(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		character.getSkills().sort((a, b) -> {
			String ca = a.getCategoryId();
			String cb = a.getCategoryId();
			if (!ca.equals(cb)) {
				return ca.compareTo(cb);
			}
			String sa = a.getSkillId();
			String sb = b.getSkillId();
			return sa.compareTo(sb);
		});
	}

}
