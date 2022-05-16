package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.npc.NpcPredefinedName;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface NpcPredefinedNameRepository extends ReactiveMongoRepository<NpcPredefinedName, String> {

	Flux<NpcPredefinedName> findByGroup(String npcNameGeneratorGroup);

}
