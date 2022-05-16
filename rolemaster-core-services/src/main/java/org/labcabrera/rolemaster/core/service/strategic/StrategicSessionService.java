package org.labcabrera.rolemaster.core.service.strategic;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.converter.StrategicSessionCreationToStrategicSession;
import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.StrategicSessionUpdate;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.exception.SessionNotFoundException;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.security.AuthorizationConsumer;
import org.labcabrera.rolemaster.core.security.ReadAuthorizationFilter;
import org.labcabrera.rolemaster.core.security.UserFriendOwnerProcessor;
import org.labcabrera.rolemaster.core.security.WriteAuthorizationFilter;
import org.labcabrera.rolemaster.core.service.MetadataCreationUpdater;
import org.labcabrera.rolemaster.core.service.tactical.TacticalCharacterService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.labcabrera.rolemaster.core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StrategicSessionService {

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
	private StrategicSessionCreationToStrategicSession converter;

	@Autowired
	private MetadataCreationUpdater metadataCreationUpdater;

	public Mono<StrategicSession> findById(JwtAuthenticationToken auth, String id) {
		return strategicSessionRepository.findById(id).flatMap(s -> readFilter.apply(auth, s));
	}

	public Flux<StrategicSession> findAll(JwtAuthenticationToken auth, Pageable pageable) {
		return userService.findById(auth.getName())
			.map(userFriendOwnerProcessor::filterOwners)
			.flatMapMany(ids -> strategicSessionRepository.findByOwner(ids, pageable.getSort()));
	}

	public Mono<StrategicSession> createSession(JwtAuthenticationToken auth, StrategicSessionCreation request) {
		return Mono.just(request)
			.map(converter::convert)
			.map(s -> authorizationConsumer.accept(auth, s))
			.map(metadataCreationUpdater::apply)
			.flatMap(strategicSessionRepository::save);
	}

	public Mono<TacticalCharacter> addCharacter(String sessionId, String characterId) {
		return characterStatusService.create(sessionId, characterId);
	}

	public Mono<Void> deleteById(JwtAuthenticationToken auth, String id) {
		return strategicSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Strategic session not found.")))
			.map(s -> writeFilter.apply(auth, s))
			.thenMany(tacticalSessionRepository.findAll())
			.flatMap(session -> tacticalService.deleteSession(session.getId()))
			.then(strategicSessionRepository.deleteById(id));
	}

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
