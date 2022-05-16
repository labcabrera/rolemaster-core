package org.labcabrera.rolemaster.core.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories("org.labcabrera.rolemaster.core.repository")
public class RolemasterCoreRepositoryConfiguration {

}
