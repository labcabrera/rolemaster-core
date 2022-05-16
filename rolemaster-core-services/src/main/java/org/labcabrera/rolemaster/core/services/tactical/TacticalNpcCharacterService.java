package org.labcabrera.rolemaster.core.services.tactical;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;

import reactor.core.publisher.Mono;

public interface TacticalNpcCharacterService {

	Mono<TacticalCharacter> create(@NotEmpty String tacticalSessionId, @NotNull @Valid Npc npc);

	Mono<TacticalCharacter> create(@NotEmpty String tacticalSessionId, @NotNull @Valid Npc npc, @Valid NpcCustomization npcCustomization);

}