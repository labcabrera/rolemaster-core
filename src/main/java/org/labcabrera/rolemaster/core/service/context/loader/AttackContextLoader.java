package org.labcabrera.rolemaster.core.service.context.loader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.repository.ItemRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.service.context.AttackContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AbstractAttackProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AttackContextLoader implements AbstractAttackProcessor {

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private TacticalRoundRepository roundRepository;

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public Mono<AttackContext> apply(AttackContext context) {
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
			})
			.flatMap(this::loadTargets)
			.flatMap(this::loadSourceItems);
	}

	private Mono<AttackContext> loadTargets(AttackContext context) {
		List<String> ids = new ArrayList<>(new HashSet<>(context.getAction().getTargets().values()));
		return tacticalCharacterRepository.findAllById(ids)
			.collectList()
			.map(list -> {
				if (list.size() != ids.size()) {
					throw new BadRequestException("Invalid targets.");
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

	private Mono<AttackContext> loadSourceItems(AttackContext context) {
		List<String> listWithDuplicates = context.getSource().getItems().stream()
			.map(CharacterItem::getItemId)
			.filter(Objects::nonNull)
			.toList();
		Set<String> ids = new HashSet<>(listWithDuplicates);
		return itemRepository.findAllById(ids)
			.collectList()
			.map(list -> {
				if (list.size() != ids.size()) {
					throw new DataConsistenceException("Error reading item types.");
				}
				list.stream().forEach(e -> context.getItemMap().put(e.getId(), e));
				return context;
			});
	}
}
