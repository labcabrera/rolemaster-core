package org.labcabrera.rolemaster.core.service.tactical;

import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;

import reactor.core.publisher.Mono;

public interface TacticalActionService {

	Mono<TacticalRound> getDeclaredAction(String tacticalSessionId, String source, TacticalActionPhase priority);

	Mono<TacticalRound> removeDeclaredAction(String tacticalSessionId, String source, TacticalActionPhase priority);

	Mono<TacticalRound> delare(String tacticalSessionId, TacticalActionDeclaration actionDeclaration);

	void setInitiative(String tacticalSessionId, String tacticalCharacterContextId, int initiativeRoll);

	Mono<TacticalRound> execute(String tacticalSessionId, String id, TacticalActionPhase normal, TacticalActionExecution execution);

	void startExecutionPhase(String tacticalSessionId);

}