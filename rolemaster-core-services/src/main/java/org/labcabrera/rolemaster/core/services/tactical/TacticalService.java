package org.labcabrera.rolemaster.core.services.tactical;

import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TacticalService {

	Mono<TacticalSession> createSession(JwtAuthenticationToken auth, TacticalSessionCreation request);

	Mono<Void> deleteSession(String tacticalSessionId);

	Mono<TacticalCharacter> addCharacter(String tacticalSessionId, String characterId);

	Mono<TacticalCharacter> addNpc(String tacticalSessionId, String npcId);

	Mono<TacticalCharacter> addNpc(String tacticalSessionId, String npcId, NpcCustomization npcCustomization);

	Mono<TacticalRound> startRound(String tacticalSessionId);

	Mono<TacticalRound> getCurrentRound(String tacticalSessionId);

	Flux<TacticalAction> getActionQueue(String roundId);

}