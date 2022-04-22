package org.labcabrera.rolemaster.core.service.tactical.impl.maneuvers;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.dto.action.execution.StaticManeuverExecution;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovingManeuver;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionStaticManeuver;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class StaticManeuverExecutionService {

	public Mono<TacticalActionMovingManeuver> execute(TacticalActionStaticManeuver action, StaticManeuverExecution execution) {
		//TODO
		throw new NotImplementedException();
	}
}
