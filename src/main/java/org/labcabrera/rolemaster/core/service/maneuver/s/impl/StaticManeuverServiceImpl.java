package org.labcabrera.rolemaster.core.service.maneuver.s.impl;

import java.util.List;

import org.labcabrera.rolemaster.core.model.maneuver.s.StaticManeuverContext;
import org.labcabrera.rolemaster.core.model.maneuver.s.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.s.StaticManeuverResult;
import org.labcabrera.rolemaster.core.service.maneuver.s.StaticManeuverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaticManeuverServiceImpl implements StaticManeuverService {

	@Autowired
	private List<StaticManeuverModifierProcessor> processors;

	@Override
	public StaticManeuverResult apply(StaticManeuverRequest request) {
		StaticManeuverContext context = StaticManeuverContext.builder()
			.request(request)
			.build();
		processors.stream().forEach(e -> e.accept(context));

		StaticManeuverResult result = StaticManeuverResult.builder()
			.modifiers(context.getModifiers())
			.build();
		return result;
	}

}
