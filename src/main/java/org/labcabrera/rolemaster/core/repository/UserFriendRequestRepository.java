package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.UserFriendRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserFriendRequestRepository extends ReactiveMongoRepository<UserFriendRequest, String> {

}
