package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.repository.NpcPredefinedNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class NpcNameGenerator {

	@Autowired
	private NpcPredefinedNameRepository npcPredefinedNameRepository;

	public Mono<String> generateName(String tacticalSessionId, Npc npc) {
		if (npc.getUnique()) {
			return Mono.just(npc.getName());
		}
		if (npc.getNpcNameGeneratorGroup() != null) {
			//TODO check duplicates
			return npcPredefinedNameRepository.findByGroup(npc.getNpcNameGeneratorGroup())
				.collectList()
				.map(list -> {
					List<String> tmp = list.stream().map(e -> e.getName()).collect(Collectors.toList());
					Collections.shuffle(tmp);
					return tmp;
				})
				.map(list -> {
					return list.isEmpty() ? generateRandomName(npc) : list.iterator().next();
				});
		}
		return Mono.just("todo");
	}

	private String generateRandomName(Npc npc) {
		return npc.getName() + " (" + new Random().nextInt(100) + ")";
	}

}
