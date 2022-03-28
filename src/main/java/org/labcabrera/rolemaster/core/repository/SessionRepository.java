package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.session.Session;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SessionRepository extends ReactiveMongoRepository<Session, String> {

}
