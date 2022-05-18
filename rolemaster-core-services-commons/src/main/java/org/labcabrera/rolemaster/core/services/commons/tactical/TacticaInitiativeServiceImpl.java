package org.labcabrera.rolemaster.core.services.commons.tactical;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.context.TacticalContext;
import org.labcabrera.rolemaster.core.dto.tactical.InitiativeDeclaration;
import org.labcabrera.rolemaster.core.dto.tactical.TacticalCharacterInitiativeDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRoundCharacterInitiative;
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

	@Override
	public Mono<TacticalRound> initiativeDeclaration(JwtAuthenticationToken auth, String tacticalSessionId,
		InitiativeDeclaration initiativeDeclaration) {
		return tacticalContextLoader.apply(auth, tacticalSessionId, true, true)
			.map(ctx -> loadInitiatives(ctx, initiativeDeclaration))
			.flatMap(this::saveRound)
			.map(ctx -> ctx.getTacticalRound());
	}

	public TacticalContext loadInitiatives(TacticalContext context, InitiativeDeclaration initiativeDeclaration) {
		TacticalRound round = context.getTacticalRound();
		context.getCharacters().stream().forEach(character -> {
			var opt = initiativeDeclaration.findByCharacterId(character.getCharacterId())
				.orElse(TacticalCharacterInitiativeDeclaration.builder().initiativeRoll(0).build());
			int roll = opt.getInitiativeRoll();
			int baseModifier = character.getModifiers().getInitiative();
			Map<String, Integer> customModifiers = opt.getCustomModifiers();
			TacticalRoundCharacterInitiative initiative = new TacticalRoundCharacterInitiative();
			initiative.getBonus().put("base", baseModifier);
			initiative.getBonus().put("roll", roll);
			initiative.getBonus().putAll(customModifiers);
			round.getInitiatives().put(character.getId(), initiative);
		});
		return context;

	}

	private Mono<TacticalContext> saveRound(TacticalContext context) {
		return tacticalRoundRepository.save(context.getTacticalRound()).thenReturn(context);
	}

}
