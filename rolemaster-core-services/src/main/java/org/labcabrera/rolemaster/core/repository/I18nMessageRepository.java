package org.labcabrera.rolemaster.core.repository;

import org.labcabrera.rolemaster.core.model.i18n.I18nMessage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface I18nMessageRepository extends ReactiveMongoRepository<I18nMessage, String> {
	
	

}
