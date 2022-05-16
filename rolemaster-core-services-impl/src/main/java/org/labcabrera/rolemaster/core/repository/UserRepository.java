package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
