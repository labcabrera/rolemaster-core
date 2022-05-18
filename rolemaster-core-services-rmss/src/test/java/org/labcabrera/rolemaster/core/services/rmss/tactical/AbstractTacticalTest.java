package org.labcabrera.rolemaster.core.services.rmss.tactical;

import java.math.BigDecimal;

import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.TemperatureMultiplier;
import org.labcabrera.rolemaster.core.model.tactical.TerrainType;
import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.services.rmss.MockAuthentication;
import org.labcabrera.rolemaster.core.services.strategic.StrategicSessionService;
import org.labcabrera.rolemaster.core.services.tactical.TacticalActionService;
import org.labcabrera.rolemaster.core.services.tactical.TacticalInitiativeService;
import org.labcabrera.rolemaster.core.services.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public abstract class AbstractTacticalTest {

	protected JwtAuthenticationToken auth = MockAuthentication.mock();

	@Autowired
	protected StrategicSessionService strategicSessionService;

	@Autowired
	protected TacticalService tacticalService;

	@Autowired
	protected TacticalActionService tacticalActionService;

	@Autowired
	protected StrategicSessionRepository strategicSessionRepository;

	@Autowired
	protected TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	protected TacticalRoundRepository roundRepository;

	@Autowired
	protected TacticalActionRepository tacticalActionRepository;

	@Autowired
	protected TacticalCharacterRepository tacticalCharacterRepository;

	@Autowired
	protected TacticalInitiativeService tacticalInitiativeService;

	protected StrategicSession sts;

	protected TacticalSession ts;

	protected void clearData() {
		strategicSessionRepository.deleteAll()
			.then(tacticalSessionRepository.deleteAll())
			.then(roundRepository.deleteAll())
			.then(tacticalActionRepository.deleteAll())
			.then(tacticalCharacterRepository.deleteAll())
			.share().block();
	}

	protected void prepare() {

		sts = strategicSessionService.createSession(auth, StrategicSessionCreation.builder()
			.name("Test strategic session")
			.universeId("middle-earth")
			.description("Testing")
			.build()).share().block();

		ts = tacticalService.createSession(auth, TacticalSessionCreation.builder()
			.strategicSessionId(sts.getId())
			.name("Test tactical session")
			.scale(0.5)
			.description("Testing")
			.temperature(TemperatureMultiplier.NORMAL)
			.terrain(TerrainType.NORMAL)
			.exhaustionMultiplier(BigDecimal.ONE)
			.build()).share().block();
	}
}
