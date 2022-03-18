package org.labcabrera.rolemaster.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI openAPIInfo() {
		OpenAPI openApi = new OpenAPI()
			.info(new Info().title("Rolemaster core API")
				.description("")
				.contact(new Contact()
					.name("Luis Cabrera")
					.url("https://github.com/labcabrera/rolemaster-core")
					.email("lab.cabrera@gmail.com"))
				.license(new License()
					.name("GPL"))
				.version("1.0.0"));
		return openApi;
	}

}
