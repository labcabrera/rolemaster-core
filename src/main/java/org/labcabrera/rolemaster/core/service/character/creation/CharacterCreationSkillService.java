package org.labcabrera.rolemaster.core.service.character.creation;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.springframework.stereotype.Service;

@Service
public class CharacterCreationSkillService {

	public CharacterModificationContext initialize(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		context.getSkills().stream().forEach(skill -> {
			CharacterSkill ci = CharacterSkill.builder()
				.skillId(skill.getId())
				.build();
			character.getSkills().add(ci);
		});
		return context;
	}
}
