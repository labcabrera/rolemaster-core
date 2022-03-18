package org.labcabrera.rolemaster.core;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class RolemasteCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(RolemasteCoreApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routerFunction() {
		return route(GET("/"), req -> ServerResponse.temporaryRedirect(URI.create("/swagger-ui.html")).build());
	}

}
