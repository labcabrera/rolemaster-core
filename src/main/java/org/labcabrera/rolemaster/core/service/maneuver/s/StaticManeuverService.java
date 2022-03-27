package org.labcabrera.rolemaster.core.service.maneuver.s;

import java.util.function.Function;

import org.labcabrera.rolemaster.core.model.maneuver.s.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.s.StaticManeuverResult;

public interface StaticManeuverService extends Function<StaticManeuverRequest, StaticManeuverResult> {
}
