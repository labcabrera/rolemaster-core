package org.labcabrera.rolemaster.core.services.commons.strategic;

import java.time.LocalDateTime;
import java.util.logging.Level;

import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.StrategicSessionUpdate;
import org.labcabrera.rolemaster.core.model.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.exception.SessionNotFoundException;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.services.commons.Messages.Errors;
import org.labcabrera.rolemaster.core.services.commons.MetadataCreationUpdater;
import org.labcabrera.rolemaster.core.services.commons.security.AuthorizationConsumer;
import org.labcabrera.rolemaster.core.services.commons.security.ReadAuthorizationFilter;
import org.labcabrera.rolemaster.core.services.commons.security.UserFriendOwnerProcessor;
import org.labcabrera.rolemaster.core.services.commons.security.WriteAuthorizationFilter;
import org.labcabrera.rolemaster.core.services.commons.user.UserService;
import org.labcabrera.rolemaster.core.services.strategic.StrategicSessionService;
import org.labcabrera.rolemaster.core.services.tactical.TacticalCharacterService;
import org.labcabrera.rolemaster.core.services.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StrategicSessionServiceImpl implements StrategicSessionService {

	@Autowired
	private UserService userService;

	@Autowired
	private StrategicSessionRepository strategicSessionRepository;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private TacticalService tacticalService;

	@Autowired
	private TacticalCharacterService characterStatusService;

	@Autowired
	private ReadAuthorizationFilter readFilter;

	@Autowired
	private WriteAuthorizationFilter writeFilter;

	@Autowired
	private AuthorizationConsumer authorizationConsumer;

	@Autowired
	private UserFriendOwnerProcessor userFriendOwnerProcessor;

	@Autowired
	private Converter<StrategicSessionCreation, StrategicSession> converter;

	@Autowired
	private MetadataCreationUpdater metadataCreationUpdater;

	@Override
	public Mono<StrategicSession> findById(JwtAuthenticationToken auth, String id) {
		return strategicSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.missingStrategicSession(id))))
			.flatMap(s -> readFilter.apply(auth, s))
			.doOnNext(e -> System.out.println(e))
			.log("test", Level.INFO);
	}

	@Override
	public Flux<StrategicSession> findAll(JwtAuthenticationToken auth, Pageable pageable) {
		return userService.findById(auth.getName())
			.map(userFriendOwnerProcessor::filterOwners)
			.flatMapMany(ids -> strategicSessionRepository.findByOwner(ids, pageable.getSort()));
	}

	@Override
	public Mono<StrategicSession> createSession(JwtAuthenticationToken auth, StrategicSessionCreation request) {
		return Mono.just(request)
			.map(converter::convert)
			.map(s -> authorizationConsumer.accept(auth, s))
			.map(metadataCreationUpdater::apply)
			.flatMap(strategicSessionRepository::save);
	}

	@Override
	public Mono<TacticalCharacter> addCharacter(String sessionId, String characterId) {
		return characterStatusService.create(sessionId, characterId);
	}

	@Override
	public Mono<Void> deleteById(JwtAuthenticationToken auth, String id) {
		return strategicSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Strategic session not found.")))
			.map(s -> writeFilter.apply(auth, s))
			.thenMany(tacticalSessionRepository.findAll())
			.flatMap(session -> tacticalService.deleteSession(session.getId()))
			.then(strategicSessionRepository.deleteById(id));
	}

	@Override
	public Mono<StrategicSession> updateSession(String id, StrategicSessionUpdate request) {
		return strategicSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(new SessionNotFoundException(id)))
			.map(s -> {
				s.setName(request.getName() != null ? request.getName() : s.getName());
				s.setDescription(request.getDescription() != null ? request.getDescription() : s.getDescription());
				s.getMetadata().setUpdated(LocalDateTime.now());
				return s;
			})
			.flatMap(strategicSessionRepository::save);
	}

}
