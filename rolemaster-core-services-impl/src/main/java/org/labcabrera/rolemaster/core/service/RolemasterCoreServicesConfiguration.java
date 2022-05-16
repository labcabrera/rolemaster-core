package org.labcabrera.rolemaster.core.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@ComponentScan({ "org.labcabrera.rolemaster.core.service" })
@EnableReactiveMongoRepositories("org.labcabrera.rolemaster.core.repository")
public class RolemasterCoreServicesConfiguration {

}
