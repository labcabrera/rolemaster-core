package org.labcabrera.rolemaster.core.services.commons.converter;

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
			.version(request.getVersion())
			.universeId(request.getUniverseId())
			.description(request.getDescription())
			.build();
	}

}
