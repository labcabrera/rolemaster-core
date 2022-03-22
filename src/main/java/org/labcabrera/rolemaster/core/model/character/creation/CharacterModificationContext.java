package org.labcabrera.rolemaster.core.model.character.creation;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.model.character.Race;

public interface CharacterModificationContext {

	CharacterInfo getCharacter();

	Race getRace();

	Profession getProfession();

	void setRace(Race race);

	void setProfession(Profession profession);

}
