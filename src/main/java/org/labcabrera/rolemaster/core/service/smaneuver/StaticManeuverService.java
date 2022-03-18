package org.labcabrera.rolemaster.core.service.smaneuver;

import java.util.function.Function;

import org.labcabrera.rolemaster.core.model.smaneuver.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.smaneuver.StaticManeuverResult;

public interface StaticManeuverService extends Function<StaticManeuverRequest, StaticManeuverResult> {
}
