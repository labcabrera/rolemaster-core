package org.labcabrera.rolemaster.core.service.tactical;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.model.tactical.TacticalNpcInstance;
import org.labcabrera.rolemaster.core.repository.NpcRepository;
import org.labcabrera.rolemaster.core.repository.TacticalNpcInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalNpcInstanceService {

	@Autowired
	private NpcRepository npcRepository;

	@Autowired
	private TacticalNpcInstanceRepository repository;

	public Mono<TacticalNpcInstance> create(String npcId) {
		return npcRepository.findById(npcId)
			.switchIfEmpty(Mono.error(new BadRequestException("Invalid npc identifier.")))
			.zipWith(repository.countByNpcId(npcId))
			.map(pair -> {
				Npc npc = pair.getT1();
				Long count = pair.getT2();
				String name = npc.getUnique() ? npc.getName() : String.format("%s - %s", npc.getName(), count + 1);
				return TacticalNpcInstance.builder()
					.npcId(npcId)
					.name(name)
					.metadata(EntityMetadata.builder()
						.created(LocalDateTime.now())
						.build())
					.build();
			})
			.flatMap(repository::insert);
	}

}
