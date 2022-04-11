package org.labcabrera.rolemaster.core.service.tactical;

import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;

import reactor.core.publisher.Mono;

public interface TacticalService {

	Mono<TacticalSession> createSession(TacticalSessionCreation request);

	Mono<TacticalCharacterContext> addCharacter(String tacticalSessionId, String characterId);

	Mono<TacticalCharacterContext> addNpc(String tacticalSessionId, String npcId);

	Mono<TacticalRound> startRound(String tacticalSessionId);

	@Deprecated
	Mono<TacticalRound> declare(String tacticalSessionId, TacticalAction action);

	Mono<TacticalRound> getCurrentRound(String tacticalSessionId);

	@Deprecated
	Mono<TacticalRound> setInitiatives(String tacticalSessionId, Map<String, Integer> initiatives);

	@Deprecated
	Mono<TacticalRound> generateRandomInitiatives(String tacticalSessionId);

	Mono<List<TacticalAction>> getActionQueue(String tacticalSessionId);

}