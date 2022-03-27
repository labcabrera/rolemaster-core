package org.labcabrera.rolemaster.core.service.maneuver.m;

import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverResult;

import reactor.core.publisher.Mono;

public interface MovingManeuverService {

	Mono<MovingManeuverResult> apply(MovingManeuverRequest request);

}
