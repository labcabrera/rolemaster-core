package org.labcabrera.rolemaster.core.services.commons.context;

import org.labcabrera.rolemaster.core.dto.context.TacticalContext;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.exception.NotFoundException;
import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.services.commons.security.ReadAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class TacticalContextLoader {

	@Autowired
	private StrategicSessionRepository strategicSessionRepository;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private TacticalRoundRepository tacticalRoundRepository;

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	@Autowired
	private TacticalActionRepository tacticalActionRepository;

	@Autowired
	private ReadAuthorizationFilter readFilter;

	public Mono<TacticalContext> apply(JwtAuthenticationToken auth, String tacticalSessionId, boolean loadCharacters, boolean loadActions) {
		return tacticalSessionRepository.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session not found.")))
			.map(tacticalSession -> TacticalContext.builder().tacticalSession(tacticalSession).build())
			.flatMap(ctx -> loadStrategicSession(auth, ctx))
			.flatMap(this::loadRound)
			.flatMap(ctx -> loadCharacters(ctx, loadCharacters))
			.flatMap(ctx -> loadActions(ctx, loadActions));
	}

	public Mono<TacticalContext> loadStrategicSession(JwtAuthenticationToken auth, TacticalContext context) {
		return strategicSessionRepository.findById(context.getTacticalSession().getStrategicSessionId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Strategic session not found.")))
			.flatMap(strategicSession -> readFilter.apply(auth, strategicSession))
			.map(strategicSession -> {
				context.setStrategicSession(strategicSession);
				return context;
			});
	}

	public Mono<TacticalContext> loadRound(TacticalContext context) {
		return tacticalRoundRepository.findFirstByTacticalSessionIdOrderByRoundDesc(context.getTacticalSession().getId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Round not found.")))
			.map(round -> {
				context.setTacticalRound(round);
				return context;
			});
	}

	public <E extends TacticalContext> Mono<E> loadCharacters(E context, boolean loadCharacters) {
		if (!loadCharacters) {
			return Mono.just(context);
		}
		return tacticalCharacterRepository.findByTacticalSessionId(context.getTacticalSession().getId()).collectList()
			.map(list -> {
				context.setCharacters(list);
				return context;
			});
	}

	public <E extends TacticalContext> Mono<E> loadActions(E context, boolean loadActions) {
		if (!loadActions) {
			return Mono.just(context);
		}
		return tacticalActionRepository.findByRoundId(context.getTacticalRound().getId()).collectList()
			.map(list -> {
				context.setRoundActions(list);
				return context;
			});
	}

}
