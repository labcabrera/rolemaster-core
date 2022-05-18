package org.labcabrera.rolemaster.core.services.commons.context;

import org.labcabrera.rolemaster.core.dto.context.TacticalContext;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
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
		TacticalContext context = new TacticalContext();
		return Mono.just(context)
			.flatMap(ctx -> loadTacticalSession(ctx, tacticalSessionId))
			.flatMap(ctx -> loadStrategicSession(auth, ctx, ctx.getTacticalSession().getStrategicSessionId()))
			.flatMap(ctx -> loadRoundByTacticalSessionId(ctx, tacticalSessionId))
			.flatMap(ctx -> loadCharacters(ctx, ctx.getTacticalSession().getId(), loadCharacters))
			.flatMap(ctx -> loadActions(ctx, ctx.getTacticalRound().getId(), loadActions));
	}

	protected <E extends TacticalContext> Mono<E> loadTacticalSession(E context, String tacticalSessionId) {
		return tacticalSessionRepository.findById(tacticalSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Tactical session not found.")))
			.map(tacticalSession -> {
				context.setTacticalSession(tacticalSession);
				return context;
			});
	}

	protected <E extends TacticalContext> Mono<E> loadStrategicSession(JwtAuthenticationToken auth, E context, String strategicSessionId) {
		return strategicSessionRepository.findById(strategicSessionId)
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Strategic session not found.")))
			.flatMap(strategicSession -> readFilter.apply(auth, strategicSession))
			.map(strategicSession -> {
				context.setStrategicSession(strategicSession);
				return context;
			});
	}

	protected <E extends TacticalContext> Mono<E> loadRoundById(E context, String roundId) {
		return tacticalRoundRepository.findById(roundId)
			.switchIfEmpty(Mono.error(() -> new DataConsistenceException(String.format("Round %s not found.", roundId))))
			.map(round -> {
				context.setTacticalRound(round);
				return context;
			});
	}

	protected <E extends TacticalContext> Mono<E> loadRoundByTacticalSessionId(E context, String tacticalSessionId) {
		return tacticalRoundRepository.findFirstByTacticalSessionIdOrderByRoundDesc(tacticalSessionId)
			.switchIfEmpty(
				Mono.error(() -> new DataConsistenceException(String.format("Tactical session %s has no rounds.", tacticalSessionId))))
			.map(round -> {
				context.setTacticalRound(round);
				return context;
			});
	}

	protected <E extends TacticalContext> Mono<E> loadCharacters(E context, String tacticalSessionId, boolean loadCharacters) {
		if (!loadCharacters) {
			return Mono.just(context);
		}
		return tacticalCharacterRepository.findByTacticalSessionId(tacticalSessionId).collectList()
			.map(list -> {
				context.setCharacters(list);
				return context;
			});
	}

	protected <E extends TacticalContext> Mono<E> loadActions(E context, String tacticalRoundId, boolean loadActions) {
		if (!loadActions) {
			return Mono.just(context);
		}
		return tacticalActionRepository.findByRoundId(tacticalRoundId).collectList()
			.map(list -> {
				context.setRoundActions(list);
				return context;
			});
	}

}
