package org.labcabrera.rolemaster.core.services.rmu.character.creation;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.dto.character.CharacterCreation;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.services.character.creation.CharacterCreationService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CharacterCreationServiceRmuImpl implements CharacterCreationService {

	@Override
	public Mono<CharacterInfo> create(@NotNull JwtAuthenticationToken auth, @Valid @NotNull CharacterCreation request) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	@Override
	public List<RolemasterVersion> compatibleVersions() {
		return Arrays.asList(RolemasterVersion.RMU);
	}

}
