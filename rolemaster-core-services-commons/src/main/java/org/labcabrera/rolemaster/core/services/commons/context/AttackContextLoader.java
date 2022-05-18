package org.labcabrera.rolemaster.core.services.commons.context;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AttackContextLoader {

	@Autowired
	private TacticalContextLoader contextLoader;

	@Autowired
	private ItemRepository itemRepository;

	public Mono<AttackContext> apply(JwtAuthenticationToken auth, TacticalActionAttack attack) {
		AttackContext context = new AttackContext();
		context.setAction(attack);
		return apply(auth, context);
	}

	public Mono<AttackContext> apply(JwtAuthenticationToken auth, AttackContext context) {
		return Mono.just(context)
			.flatMap(ctx -> contextLoader.loadRoundById(ctx, ctx.getAction().getRoundId()))
			.flatMap(ctx -> contextLoader.loadTacticalSession(ctx, ctx.getTacticalRound().getTacticalSessionId()))
			.flatMap(ctx -> contextLoader.loadStrategicSession(auth, ctx, ctx.getTacticalSession().getStrategicSessionId()))
			.flatMap(ctx -> contextLoader.loadCharacters(ctx, ctx.getTacticalSession().getId(), true))
			.flatMap(ctx -> contextLoader.loadActions(ctx, ctx.getTacticalRound().getId(), true))
			.flatMap(this::loadSourceItems)
			.map(this::loadTargets);
	}

	private AttackContext loadTargets(AttackContext context) {
		context.getAction().getTargets().entrySet().stream().forEach(entry -> {
			AttackTargetType attackTargetType = entry.getKey();
			TacticalCharacter character = context.getCharacter(entry.getValue());
			context.getTargets().put(attackTargetType, character);
		});
		return context;
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
