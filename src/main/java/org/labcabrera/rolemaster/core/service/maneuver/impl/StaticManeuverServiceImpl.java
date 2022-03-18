package org.labcabrera.rolemaster.core.service.maneuver.impl;

import java.util.List;

import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverResult;
import org.labcabrera.rolemaster.core.service.maneuver.StaticManeuverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaticManeuverServiceImpl implements StaticManeuverService {

	@Autowired
	private List<StaticManeuverModifierProcessor> processors;

	@Override
	public StaticManeuverResult apply(StaticManeuverRequest request) {
		StaticManeuverResult result = StaticManeuverResult.builder().build();
		processors.stream().forEach(e -> e.accept(request, result));
		return result;
	}

}
