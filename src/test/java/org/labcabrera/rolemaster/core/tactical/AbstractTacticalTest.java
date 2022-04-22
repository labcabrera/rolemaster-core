package org.labcabrera.rolemaster.core.tactical;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.service.strategic.StrategicSessionService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalActionService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTacticalTest {

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
		sts = strategicSessionService.createSession(StrategicSessionCreation.builder()
			.name("Test strategic session " + LocalDateTime.now())
			.description("Testing")
			.build()).share().block();

		ts = tacticalService.createSession(TacticalSessionCreation.builder()
			.strategicSessionId(sts.getId())
			.name("Test tactical session " + LocalDateTime.now())
			.description("Testing")
			.build()).share().block();
	}
}