package org.labcabrera.rolemaster.core.service.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
class NpcPopulator extends AbstractJsonPopulator<Npc> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/npcs/npcs-generic.json",
			"data/populator/npcs/npcs-middle-earth-elves.json",
			"data/populator/npcs/npcs-middle-earth-humans.json",
			"data/populator/npcs/npcs-middle-earth-orcs.json",
			"data/populator/npcs/npcs-middle-earth-characters-balrogs.json",
			"data/populator/npcs/npcs-middle-earth-characters-dunedain.json",
			"data/populator/npcs/npcs-middle-earth-characters-elves.json",
			"data/populator/npcs/npcs-middle-earth-characters-evil.json",
			"data/populator/npcs/npcs-middle-earth-characters-hobbits.json",
			"data/populator/npcs/npcs-middle-earth-characters-nazgul.json",
			"data/populator/npcs/npcs-middle-earth-characters-other.json");
	}

	@Override
	protected TypeReference<List<Npc>> getTypeReference() {
		return new TypeReference<List<Npc>>() {
		};
	}
}
