package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AbstractAttackProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AttackContextLoader extends AbstractAttackProcessor {

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	@Override
	public Mono<AttackContext> apply(AttackContext context) {
		return Mono.just(context)
			.zipWith(tacticalCharacterRepository.findById(context.getAction().getSource()), (a, b) -> a.setSource(b))
			.flatMap(this::loadTargets);
	}

	private Mono<AttackContext> loadTargets(AttackContext context) {
		List<String> ids = new ArrayList<>(new HashSet<>(context.getAction().getTargets().values()));
		return tacticalCharacterRepository.findAllById(ids).collectList()
			.map(list -> {
				if (list.size() != ids.size()) {
					throw new BadRequestException("Invalid targets");
				}
				context.getAction().getTargets().entrySet().stream().forEach(e -> {
					AttackTargetType key = e.getKey();
					String value = e.getValue();
					TacticalCharacter tc = list.stream().filter(i -> value.equals(e.getValue())).findFirst().orElseThrow();
					context.getTargets().put(key, tc);
				});
				return context;
			})
			.flatMap(ctx -> Mono.just(context));
	}
}
