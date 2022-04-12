package org.labcabrera.rolemaster.core.service.tactical;

import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;

import reactor.core.publisher.Mono;

public interface TacticalNpcCharacterService {

	Mono<TacticalCharacter> create(String tacticalSessionId, Npc npc);

	Mono<TacticalCharacter> create(String tacticalSessionId, Npc npc, NpcCustomization npcCustomization);

}