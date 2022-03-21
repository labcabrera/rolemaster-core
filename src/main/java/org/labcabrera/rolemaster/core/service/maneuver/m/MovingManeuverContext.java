package org.labcabrera.rolemaster.core.service.maneuver.m;

import java.util.Optional;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.labcabrera.rolemaster.core.model.maneuver.HasManeuverModifiers;

public interface MovingManeuverContext extends HasManeuverModifiers {

	Optional<CharacterStatus> getCharacterStatus();

	void setCharacterStatus(Optional<CharacterStatus> value);

	Optional<CharacterInfo> getCharacterInfo();

	void setCharacterInfo(Optional<CharacterInfo> value);

}
