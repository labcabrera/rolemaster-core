package org.labcabrera.rolemaster.core.services.rmu.character.creation;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.dto.character.CharacterCreation;
import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.labcabrera.rolemaster.core.services.character.creation.CharacterCreationService;
import org.labcabrera.rolemaster.core.services.commons.context.CharacterModificationContextLoader;
import org.labcabrera.rolemaster.core.services.commons.converter.CharacterCreationToCharacterInfoConverter;
import org.labcabrera.rolemaster.core.services.commons.security.AuthorizationConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CharacterCreationServiceRmuImpl implements CharacterCreationService {

	@Autowired
	private CharacterCreationToCharacterInfoConverter converter;

	@Autowired
	private AuthorizationConsumer authConsumer;

	@Autowired
	private List<CharacterUpdatePostProcessor> postProcessors;

	@Autowired
	private CharacterInfoRepository characterInfoRepository;

	@Autowired
	private CharacterModificationContextLoader contextLoader;

	@Override
	public Mono<CharacterInfo> create(JwtAuthenticationToken auth, CharacterCreation request) {
		return Mono.just(request)
			.map(converter::convert)
			.flatMap(contextLoader::apply)
			.map(this::executePostProcessors)
			.map(CharacterModificationContext::getCharacter)
			.map(c -> authConsumer.accept(auth, c))
			.flatMap(characterInfoRepository::save);
	}

	@Override
	public List<RolemasterVersion> compatibleVersions() {
		return Arrays.asList(RolemasterVersion.RMU);
	}

	private CharacterModificationContext executePostProcessors(CharacterModificationContext context) {
		postProcessors.stream().forEach(processor -> processor.accept(context));
		return context;
	}

}
