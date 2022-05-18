package org.labcabrera.rolemaster.core.services.commons.tactical;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import org.labcabrera.rolemaster.core.dto.context.TacticalContext;
import org.labcabrera.rolemaster.core.dto.tactical.InitiativeDeclaration;
import org.labcabrera.rolemaster.core.dto.tactical.TacticalCharacterInitiativeDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRoundCharacterInitiative;
import org.labcabrera.rolemaster.core.model.tactical.action.InitiativeModifier;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.services.commons.context.TacticalContextLoader;
import org.labcabrera.rolemaster.core.services.tactical.TacticalInitiativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticaInitiativeServiceImpl implements TacticalInitiativeService {

	@Autowired
	private TacticalContextLoader tacticalContextLoader;

	@Autowired
	private TacticalRoundRepository tacticalRoundRepository;

	@Autowired
	private TacticalActionRepository tacticalActionRepository;

	@Override
	public Mono<TacticalRound> initiativeDeclaration(JwtAuthenticationToken auth, String tacticalSessionId,
		InitiativeDeclaration initiativeDeclaration) {
		return tacticalContextLoader.apply(auth, tacticalSessionId, true, true)
			.map(ctx -> loadInitiatives(ctx, initiativeDeclaration))
			.map(this::loadInitiativeActions)
			.flatMap(this::saveRound)
			.flatMap(this::saveActions)
			.map(TacticalContext::getTacticalRound);
	}

	public TacticalContext loadInitiatives(TacticalContext context, InitiativeDeclaration initiativeDeclaration) {
		TacticalRound round = context.getTacticalRound();
		context.getCharacters().stream().forEach(character -> {
			String tcId = character.getId();
			int roll = 0;
			int baseModifier = character.getModifiers().getInitiative();
			Map<InitiativeModifier, Integer> customModifiers = new EnumMap<>(InitiativeModifier.class);
			if (round.getInitiatives().containsKey(tcId)) {
				Map<InitiativeModifier, Integer> bonusMap = round.getInitiatives().get(tcId).getBonus();
				roll = bonusMap.getOrDefault(InitiativeModifier.ROLL, 0);
				customModifiers = bonusMap;
			}
			Optional<TacticalCharacterInitiativeDeclaration> opt = initiativeDeclaration.findByCharacterId(character.getId());
			if (opt.isPresent()) {
				roll = opt.get().getInitiativeRoll();
				customModifiers = opt.get().getCustomModifiers();
			}
			TacticalRoundCharacterInitiative initiative = new TacticalRoundCharacterInitiative();
			initiative.getBonus().put(InitiativeModifier.ATTRIBUTE, baseModifier);
			initiative.getBonus().put(InitiativeModifier.ROLL, roll);
			initiative.getBonus().putAll(customModifiers);
			round.getInitiatives().put(character.getId(), initiative);
		});
		return context;
	}

	private TacticalContext loadInitiativeActions(TacticalContext context) {
		TacticalRound round = context.getTacticalRound();
		context.getRoundActions().stream().forEach(action -> {
			Map<InitiativeModifier, Integer> initiativeMap = round.getInitiatives().get(action.getSource()).getBonus();
			action.setInitiativeMap(initiativeMap);
		});
		return context;
	}

	private Mono<TacticalContext> saveRound(TacticalContext context) {
		return tacticalRoundRepository.save(context.getTacticalRound()).thenReturn(context);
	}

	private Mono<TacticalContext> saveActions(TacticalContext context) {
		return tacticalActionRepository.saveAll(context.getRoundActions()).collectList().thenReturn(context);

	}

}
