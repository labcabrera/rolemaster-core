package org.labcabrera.rolemaster.core.services.commons.npc;

import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.repository.NpcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class NpcService {

	@Autowired
	private NpcRepository npcRepository;

	public Mono<Npc> findById(String id) {
		return npcRepository.findById(id);
	}

}
