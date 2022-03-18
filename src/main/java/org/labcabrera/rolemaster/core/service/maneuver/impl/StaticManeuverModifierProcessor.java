package org.labcabrera.rolemaster.core.service.maneuver.impl;

import java.util.function.BiConsumer;

import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverResult;

public interface StaticManeuverModifierProcessor extends BiConsumer<StaticManeuverRequest, StaticManeuverResult> {

}
