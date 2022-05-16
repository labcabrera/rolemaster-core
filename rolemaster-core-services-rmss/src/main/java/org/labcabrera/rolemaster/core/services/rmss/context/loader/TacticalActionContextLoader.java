package org.labcabrera.rolemaster.core.services.rmss.context.loader;

import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class TacticalActionContextLoader {

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	@Autowired
	private TacticalRoundRepository roundRepository;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	public <E extends TacticalAction> Mono<TacticalActionContext<E>> apply(E action) {
		TacticalActionContext<E> context = TacticalActionContext.<E>builder().action(action).build();
		return Mono.just(context)
			.zipWith(tacticalCharacterRepository.findById(context.getAction().getSource()), (a, b) -> {
				a.setSource(b);
				return a;
			})
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Character not found.")))
			.zipWhen(ctx -> roundRepository.findById(ctx.getAction().getRoundId()), (a, b) -> {
				a.setTacticalRound(b);
				return a;
			})
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Round not found.")))
			.zipWhen(ctx -> tacticalSessionRepository.findById(ctx.getTacticalRound().getTacticalSessionId()), (a, b) -> {
				a.setTacticalSession(b);
				return a;
			});
	}
}
