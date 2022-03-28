package org.labcabrera.rolemaster.core.service.maneuver.m.impl;

import org.labcabrera.rolemaster.core.model.tactical.CharacterStatusModifier;

public interface MovingManeuverRecord extends CharacterStatusModifier {

	String getMessage();

	Integer getDificulty();

	default Boolean getFallDown() {
		return false;
	}

	default Integer getOutRounds() {
		return 0;
	}

}
