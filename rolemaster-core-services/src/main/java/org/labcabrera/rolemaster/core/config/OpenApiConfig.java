package org.labcabrera.rolemaster.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Value("${app.openapi.version}")
	private String version;

	@Bean
	OpenAPI openAPIInfo() {
		return new OpenAPI()
			.info(new Info().title("Rolemaster core API")
				.description("Not documented yet.")
				.contact(new Contact()
					.name("Luis Cabrera - Github")
					.url("https://github.com/labcabrera/rolemaster-core")
					.email("lab.cabrera@gmail.com"))
				.license(new License()
					.name("GPL"))
				.version(version));
	}

}
