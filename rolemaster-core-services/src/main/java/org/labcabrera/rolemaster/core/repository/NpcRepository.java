package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NpcRepository extends ReactiveMongoRepository<Npc, String> {

}
