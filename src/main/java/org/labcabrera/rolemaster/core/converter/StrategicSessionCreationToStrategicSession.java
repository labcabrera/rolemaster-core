package org.labcabrera.rolemaster.core.converter;

import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StrategicSessionCreationToStrategicSession implements Converter<StrategicSessionCreation, StrategicSession> {

	@Override
	public StrategicSession convert(StrategicSessionCreation request) {
		return StrategicSession.builder()
			.name(request.getName())
			.description(request.getDescription())
			.universeId(request.getUniverseId())
			.build();
	}

}
