package org.labcabrera.rolemaster.core.service.maneuver.m.impl;

import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverResult;
import org.labcabrera.rolemaster.core.service.maneuver.m.MovingManeuverService;
import org.springframework.stereotype.Service;

@Service
public class MovingManeuverServiceImpl implements MovingManeuverService {

	@Override
	public MovingManeuverResult apply(MovingManeuverRequest request) {
		MovingManeuverResult result = MovingManeuverResult.builder().build();
		return result;
	}

}
