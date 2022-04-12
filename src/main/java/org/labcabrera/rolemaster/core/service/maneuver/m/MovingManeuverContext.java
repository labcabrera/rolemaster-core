package org.labcabrera.rolemaster.core.service.maneuver.m;

import java.util.Optional;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.maneuver.HasManeuverModifiers;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;

public interface MovingManeuverContext extends HasManeuverModifiers {

	Optional<TacticalCharacter> getCharacterStatus();

	void setCharacterStatus(Optional<TacticalCharacter> value);

	Optional<CharacterInfo> getCharacterInfo();

	void setCharacterInfo(Optional<CharacterInfo> value);

}
