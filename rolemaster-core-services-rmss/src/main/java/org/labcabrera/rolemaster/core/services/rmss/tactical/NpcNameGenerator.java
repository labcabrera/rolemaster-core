package org.labcabrera.rolemaster.core.services.rmss.tactical;

import java.security.SecureRandom;
import java.util.List;

import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.model.npc.NpcPredefinedName;
import org.labcabrera.rolemaster.core.repository.NpcPredefinedNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class NpcNameGenerator {

	@Autowired
	private NpcPredefinedNameRepository npcPredefinedNameRepository;

	private SecureRandom random = new SecureRandom();

	public Mono<String> generateName(String tacticalSessionId, Npc npc) {
		if (Boolean.TRUE.equals(npc.getUnique())) {
			return Mono.just(npc.getName());
		}
		if (npc.getNpcNameGeneratorGroup() != null) {
			return npcPredefinedNameRepository.findByGroup(npc.getNpcNameGeneratorGroup())
				.collectList()
				.map(list -> list.stream().map(NpcPredefinedName::getName).toList())
				.map(list -> list.isEmpty() ? generateRandomName(npc) : getRandomName(list));
		}
		return Mono.just("todo");
	}

	private String getRandomName(List<String> list) {
		int index = random.nextInt(list.size());
		return list.get(index);
	}

	private String generateRandomName(Npc npc) {
		//TODO check duplicates
		return npc.getName() + " (" + random.nextInt(100) + ")";
	}

}
