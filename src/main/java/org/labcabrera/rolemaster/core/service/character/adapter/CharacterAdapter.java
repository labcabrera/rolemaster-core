package org.labcabrera.rolemaster.core.service.character.adapter;

import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;

public interface CharacterAdapter {

	CharacterModificationContext apply(CharacterModificationContext context);

}
