package org.labcabrera.rolemaster.core.service.maneuver.impl;

import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverResult;
import org.labcabrera.rolemaster.core.service.maneuver.StaticManeuverService;
import org.springframework.stereotype.Service;

@Service
public class StaticManeuverServiceImpl implements StaticManeuverService {

	@Override
	public StaticManeuverResult apply(StaticManeuverRequest request) {
		StaticManeuverResult result = StaticManeuverResult.builder().build();
		loadModifiers(result, request);
		return result;
	}

	private void loadModifiers(StaticManeuverResult result, StaticManeuverRequest request) {
	}

}
