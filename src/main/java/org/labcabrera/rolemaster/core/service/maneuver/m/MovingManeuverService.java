package org.labcabrera.rolemaster.core.service.maneuver.m;

import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverResult;

public interface MovingManeuverService {

	MovingManeuverResult apply(MovingManeuverRequest request);

}
