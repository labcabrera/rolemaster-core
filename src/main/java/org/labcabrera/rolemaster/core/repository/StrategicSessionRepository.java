package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StrategicSessionRepository extends ReactiveMongoRepository<StrategicSession, String> {

}
