package org.labcabrera.rolemaster.core.services.rmss.converter;

import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSessionState;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TacticalSessionCreationToTacticalSessionConverter implements Converter<TacticalSessionCreation, TacticalSession> {

	@Override
	public TacticalSession convert(TacticalSessionCreation request) {
		return TacticalSession.builder()
			.strategicSessionId(request.getStrategicSessionId())
			.name(request.getName())
			.scale(request.getScale())
			.description(request.getDescription())
			.state(TacticalSessionState.CREATED)
			.temperature(request.getTemperature())
			.terrain(request.getTerrain())
			.exhaustionMultiplier(request.getExhaustionMultiplier())
			.breakageBonus(request.getBreakageBonus())
			.build();
	}

}
