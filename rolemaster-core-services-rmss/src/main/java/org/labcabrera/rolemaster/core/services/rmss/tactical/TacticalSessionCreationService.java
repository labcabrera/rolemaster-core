package org.labcabrera.rolemaster.core.services.rmss.tactical;

import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.services.commons.Messages.Errors;
import org.labcabrera.rolemaster.core.services.commons.MetadataCreationUpdater;
import org.labcabrera.rolemaster.core.services.commons.security.AuthorizationConsumer;
import org.labcabrera.rolemaster.core.services.commons.security.WriteAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalSessionCreationService {

	@Autowired
	private StrategicSessionRepository sessionRepository;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private WriteAuthorizationFilter writeFilter;

	@Autowired
	private Converter<TacticalSessionCreation, TacticalSession> converter;

	@Autowired
	private AuthorizationConsumer authConsumer;

	@Autowired
	private MetadataCreationUpdater metadataCreationUpdater;

	public Mono<TacticalSession> createSession(JwtAuthenticationToken auth, TacticalSessionCreation request) {
		return sessionRepository.findById(request.getStrategicSessionId())
			.switchIfEmpty(Mono.error(new BadRequestException(Errors.missingStrategicSession(request.getStrategicSessionId()))))
			.map(s -> writeFilter.apply(auth, s))
			.map(strategic -> {
				TacticalSession tactical = converter.convert(request);
				tactical.setVersion(strategic.getVersion());
				return tactical;
			})
			.map(s -> authConsumer.accept(auth, s))
			.map(metadataCreationUpdater::apply)
			.flatMap(tacticalSessionRepository::insert);
	}

}
