package org.labcabrera.rolemaster.core.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map.Entry;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import io.swagger.v3.oas.models.examples.Example;

@Configuration
public class OpenapiExamplesConfig {

	@Value("classpath:/openapi/examples/character-creation-example-01.json")
	private Resource exampleCharacterCreation01;

	@Bean
	OpenApiCustomiser openApiCustomiser(Collection<Entry<String, Example>> examples) {
		return openAPI -> {
			examples.forEach(example -> {
				openAPI.getComponents().addExamples(example.getKey(), example.getValue());
			});
		};
	}

	@Bean
	Entry<String, Example> characterCreationExample01() {
		return buildExample("characterCreationExample01", "Character creation example.", exampleCharacterCreation01);
	}

	private Entry<String, Example> buildExample(String name, String description, Resource resource) {
		Example example = new Example();
		Entry<String, Example> entry = new AbstractMap.SimpleEntry<String, Example>(name, example);
		example.setSummary(name);
		example.setDescription(description);
		example.setValue(asString(resource));
		return entry;
	}

	private String asString(Resource resource) {
		try (Reader reader = new InputStreamReader(resource.getInputStream())) {
			return FileCopyUtils.copyToString(reader);
		}
		catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}
}
