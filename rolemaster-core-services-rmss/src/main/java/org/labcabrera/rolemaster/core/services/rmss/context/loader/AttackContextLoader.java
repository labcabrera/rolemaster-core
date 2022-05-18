package org.labcabrera.rolemaster.core.services.rmss.context.loader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.repository.ItemRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.services.commons.context.TacticalContextLoader;
import org.labcabrera.rolemaster.core.services.tactical.attack.processor.AbstractAttackProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AttackContextLoader implements AbstractAttackProcessor {

	@Autowired
	private TacticalContextLoader contextLoader;

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
			.flatMap(this::loadRound)
			.flatMap(this::loadTacticalSession)
			.flatMap(ctx -> contextLoader.loadCharacters(ctx, true))
			.flatMap(ctx -> contextLoader.loadActions(ctx, true))
			.flatMap(this::loadTargets)
			.flatMap(this::loadSourceItems);
	}

	private Mono<AttackContext> loadRound(AttackContext context) {
		return roundRepository.findById(context.getAction().getRoundId())
			.switchIfEmpty(Mono.error(() -> new DataConsistenceException("Missing round id")))
			.map(round -> {
				context.setTacticalRound(round);
				return context;
			});
	}

	private Mono<AttackContext> loadTacticalSession(AttackContext context) {
		return tacticalSessionRepository.findById(context.getTacticalRound().getTacticalSessionId())
			.switchIfEmpty(Mono.error(() -> new DataConsistenceException("Missing round id")))
			.map(tacticalSession -> {
				context.setTacticalSession(tacticalSession);
				return context;
			});

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
