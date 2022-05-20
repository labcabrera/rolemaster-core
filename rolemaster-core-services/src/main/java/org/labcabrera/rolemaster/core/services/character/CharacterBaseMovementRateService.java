package org.labcabrera.rolemaster.core.services.character;

import org.labcabrera.rolemaster.core.model.RolemasterVersionService;
import org.labcabrera.rolemaster.core.model.character.BaseMovementRate;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.Race;

public interface CharacterBaseMovementRateService extends RolemasterVersionService {

	/**
	 * Return value in feets.
	 * @param character
	 * @param race
	 * @return
	 */
	BaseMovementRate getBaseMovementRate(CharacterInfo character, Race race);

}
