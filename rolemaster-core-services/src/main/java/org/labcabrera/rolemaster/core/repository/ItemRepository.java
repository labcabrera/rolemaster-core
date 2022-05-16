package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.item.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ItemRepository extends ReactiveMongoRepository<Item, String> {

}
