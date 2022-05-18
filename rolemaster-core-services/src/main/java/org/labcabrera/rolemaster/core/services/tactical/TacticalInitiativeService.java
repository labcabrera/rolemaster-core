package org.labcabrera.rolemaster.core.services.tactical;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.dto.tactical.InitiativeDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Mono;

@Validated
public interface TacticalInitiativeService {

	Mono<TacticalRound> initiativeDeclaration(
		@NotNull JwtAuthenticationToken auth,
		@NotEmpty String tacticalSessionId,
		@NotNull @Valid InitiativeDeclaration initiativeDeclaration);

}
