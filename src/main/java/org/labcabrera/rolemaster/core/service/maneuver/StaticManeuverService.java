package org.labcabrera.rolemaster.core.service.maneuver;

import java.util.function.Function;

import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverResult;

public interface StaticManeuverService extends Function<StaticManeuverRequest, StaticManeuverResult> {
}
